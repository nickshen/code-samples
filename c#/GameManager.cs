using UnityEngine;
using System.Collections;

public class GameManager : MonoBehaviour {

	public Builder build;
	public GUIManager guiManager;
	public SFX_Manager sfxManager;
	public ShopManager sm;

	public long money = 0;
	public long tapAmount = 1;
	public float autoAmount = 0;

	public GameObject moneyCount;
	public GameObject autoCount;
	private GameObject popUps;
	private GameObject tapPops;
	public Transform mineSounds;
	public Transform torches;

	private bool canMine = true;
	public bool canScroll = true;
	public bool freezeActions = false;

	private Vector3 mouseDelta = Vector3.zero;
	private Vector3 mouseLast = Vector3.zero;
	
	private float deltaTime = 0;
	private float lastTime = 0;
	private float inertiaDuration = 5;

	private float scrollVelocity = 0;
	private float scrollMultiple = 1;

	public int topCap = 90;
	public int bottomCap = -750;

	private bool returnable = true;

	private RaycastHit hit;
	private Ray ray;

	private int totalPop = 0;
	private int popIndex = 0;
	private int tapIndex = 0;

	private Camera mainCamera;
	private Vector3 cameraFirst;
	private int mineIndex;

	public Transform[] adjustments = new Transform[10];
	private long prevMoney = 0;


	void Awake() {
		float RATIO = (float)Screen.height/Screen.width;

		Application.targetFrameRate = 60;
		Debug.Log(PlayerPrefs.GetString("money"));
		money = long.Parse(PlayerPrefs.GetString("money"));
		Debug.Log ((float)Screen.height/Screen.width);

		sm = gameObject.GetComponent<ShopManager>();

		if(RATIO > 1.77 && RATIO < 1.78) { //hopefully iphone5++
			torches.localScale = new Vector3(0.7130232f, 1, 1);
			
		}
		else if(RATIO > 1.33 && RATIO < 1.34){
			Debug.Log ("ADJUST!");
			adjustments[0].localPosition = new Vector3(adjustments[0].localPosition.x + 56, adjustments[0].localPosition.y, adjustments[0].localPosition.z);
			adjustments[1].localPosition = new Vector3(adjustments[1].localPosition.x - 50, adjustments[1].localPosition.y, adjustments[1].localPosition.z);
			adjustments[2].localPosition = new Vector3(adjustments[2].localPosition.x + 46, adjustments[2].localPosition.y, adjustments[2].localPosition.z);	
		}

		//if(!PlayerPrefs.HasKey("notfirst")) {
			StartCoroutine(Tutorial());
			
		//}
	}

	IEnumerator Tutorial() {
		yield return new WaitForSeconds(1f);
		guiManager.Prompt("Master!  Help us rebuild our mine!");
		PlayerPrefs.SetString("notfirst", "done");
	}

	// Use this for initialization
	void Start () {
		prevMoney = money;
		mainCamera = Camera.main;
		mainCamera.transparencySortMode = TransparencySortMode.Orthographic;

		sfxManager = mainCamera.gameObject.GetComponent<SFX_Manager>();

		build = mainCamera.gameObject.GetComponent<Builder>();
		guiManager = mainCamera.gameObject.GetComponent<GUIManager>();

		if(moneyCount != null) ((UILabel)moneyCount.GetComponent(typeof(UILabel))).text = money.ToString("n0");
		if(autoCount != null) ((UILabel)autoCount.GetComponent(typeof(UILabel))).text = "+" + autoAmount.ToString("n0");

		
		popUps = GameObject.Find("Popups");
		tapPops = GameObject.Find("Taps");
		totalPop = popUps.transform.childCount;
	}
	
	// Update is called once per frame
	void Update () {
		//tapAmount = sm.upgradeCount[0];

		if (Input.GetButton ("Jump")) {
			if(Time.timeScale == 1) Time.timeScale = 0;
			else Time.timeScale = 1;
		}

		if(freezeActions) return;
		if(Mathf.Abs(scrollVelocity) > 0) {

			float duration = deltaTime*inertiaDuration;
			if(returnable)scrollVelocity = Mathf.Lerp(scrollVelocity, 0, duration);
			mainCamera.transform.position = new Vector3(cameraFirst.x, cameraFirst.y -= scrollVelocity *Time.deltaTime, cameraFirst.z);
		}

		if(Mathf.Abs(scrollVelocity) < 100) {
			if((mainCamera.transform.position.y) > topCap) {
				if(!Input.GetMouseButton(0)) {
					if(returnable)returnScroll('u');
				}
			}
			else if((mainCamera.transform.position.y) < bottomCap) {
				if(!Input.GetMouseButton(0)) {
					if(returnable)returnScroll('b');
				}
			}
		}

		if((mainCamera.transform.position.y) > topCap + 800) {
			mainCamera.transform.position = new Vector3(mainCamera.transform.position.x, topCap +800, mainCamera.transform.position.z);
			scrollVelocity = 0;
		}
		else if((mainCamera.transform.position.y) < bottomCap - 800) {
			mainCamera.transform.position = new Vector3(mainCamera.transform.position.x, bottomCap - 800, mainCamera.transform.position.z);
			scrollVelocity = 0;
		}

		if(Input.GetMouseButtonUp(0)) {
			if(canMine)mouseAction();

			if(Mathf.Abs(mouseDelta.y) > 2) {
				if(returnable)scrollVelocity = Mathf.Clamp( (mouseDelta.y*1.2f)/deltaTime, -10000f, 10000f);
			}

			/*if((mainCamera.transform.position.y) > topCap) {
				if(returnable)returnScroll('u');
			}*/

			scrollMultiple = 1;
			canMine = true;
		}

		if(Input.GetMouseButtonDown(0)) {

			mouseLast = Input.mousePosition;
			lastTime = Time.time;
			cameraFirst = mainCamera.transform.position;
			scrollVelocity= 0;
			returnable = true;
		}

		if(Input.GetMouseButton(0)) {
			if(returnable)scroll();
		}

		if(Input.GetButtonDown("Jump")) {
			PlayerPrefs.DeleteAll();
		}
	}

	void mouseAction() {
		ray = mainCamera.ScreenPointToRay(Input.mousePosition);

		if(Physics.Raycast(ray, out hit)) {
			if(hit.collider.gameObject.layer == 8) {
				money += tapAmount;
				Debug.Log("MINED");
				moneyUpdate();
				popup();
				mineSound();
				iTween.ScaleFrom(hit.collider.gameObject, iTween.Hash("x", 1.04f, "y", 1.04f, "z", 1.04f, "time", 0.05f));
			}
			else if(hit.collider.gameObject.tag == "Shop") {
				guiManager.LoadShop();
				sfxManager.Play("money");
			}
			else if(hit.collider.gameObject.tag == "Lab") {
				guiManager.LoadLab();
				sfxManager.Play("money");
			}
		}
	}

	void scroll() {
		if(!canScroll) {
			mouseLast = Input.mousePosition;
			//lastTime = Time.time;

			return;
		}
		mouseDelta = Input.mousePosition - mouseLast;
		deltaTime = Time.time -lastTime;
		
		// Do Stuff here
		if(mainCamera.transform.position.y > topCap) scrollMultiple = 1f/(Mathf.Clamp(1f + (mainCamera.transform.position.y *0.007f), 1, 1000));  //dampen stretch
		else if(mainCamera.transform.position.y < bottomCap) scrollMultiple = 1f/(Mathf.Clamp(1f - (mainCamera.transform.position.y *0.004f), 1, 1000));  //dampen stretch
		mainCamera.transform.position = new Vector3(cameraFirst.x, cameraFirst.y -= (mouseDelta.y * scrollMultiple), cameraFirst.z);
		
		// End do stuff
		mouseLast = Input.mousePosition;
		lastTime = Time.time;

		if(Mathf.Abs(mouseDelta.y) > 2) {
			canMine = false;
		}
	}

	void returnScroll(char direction) {
		returnable = false;
		if(direction == 'u') iTween.MoveTo(mainCamera.gameObject, new Vector3(mainCamera.transform.position.x, topCap, mainCamera.transform.position.z), 1);
		else if(direction == 'b') iTween.MoveTo(mainCamera.gameObject, new Vector3(mainCamera.transform.position.x, bottomCap, mainCamera.transform.position.z), 1);
		scrollVelocity = 0;
	}

	public void moneyUpdate() {
		if(money != prevMoney) {
			iTween.ScaleTo(moneyCount, iTween.Hash("x", 1.04f, "y", 1.04f, "z", 1.04f, "time", 0.05f));
		}
		((UILabel)moneyCount.GetComponent(typeof(UILabel))).text = money.ToString("n0");
		((UILabel)autoCount.GetComponent(typeof(UILabel))).text = "+" + autoAmount.ToString("n1");
		PlayerPrefs.SetString("money", money.ToString());
		PlayerPrefs.SetFloat("auto", autoAmount);
	}

	public void popup() {
		if(popIndex >= totalPop) popIndex = 0;
		if(tapIndex >= 16) tapIndex = 0;

		GameObject pop = tapPops.transform.GetChild(tapIndex).gameObject;

		Vector3 mousePos = Input.mousePosition;
		mousePos.z = 640;

		pop.transform.position = mainCamera.ScreenToWorldPoint(mousePos);
		pop.SetActive(true);

		GameObject popper = popUps.transform.GetChild(popIndex).gameObject;
		popper.SetActive(true);

		Vector3 targetPos = new Vector3(popper.transform.localPosition.x + Random.Range(-1, 10), popper.transform.localPosition.y + Random.Range(10, 22), popper.transform.localPosition.z);
		iTween.MoveFrom(popper, iTween.Hash("position", targetPos, "islocal", true, "time", 0.4f));

		StartCoroutine(delayHide(popper, 0.4f));

		tapIndex++;
		popIndex++;
	}

	void mineSound() {
		if(mineIndex < 16) {
			mineIndex++;
		}
		else mineIndex = 0;
		AudioSource noise = mineSounds.GetChild(mineIndex).GetComponent<AudioSource>();
		noise.pitch = Random.Range (0.8f, 2f);
		noise.Play();

	}

	IEnumerator delayHide(GameObject target, float delay) {
		yield return new WaitForSeconds(delay);
		target.SetActive(false);
	}

	public void EraseData() {

		guiManager.Prompt("Cleared Data!");

		PlayerPrefs.DeleteAll();
		Debug.Log(PlayerPrefsX.GetStringArray("built"));
		Debug.Log(PlayerPrefs.GetInt("autoAmount"));
	}
}
