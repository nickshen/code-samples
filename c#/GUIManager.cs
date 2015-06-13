using UnityEngine;
using System.Collections;

public class GUIManager : MonoBehaviour {

	public GameManager gm;

	public GameObject prompt;
	public UILabel promptLabel;

	public GameObject SHOP_scroll;
	public GameObject SHOP_general;
	public GameObject SHOP_bar;

	public GameObject LAB_scroll;
	public GameObject LAB_general;
	public GameObject LAB_bar;

	public GameObject[] vignettes = new GameObject[3];

	public Vector3 oldBar, oldScroll, oldGeneral;

	void Awake() {
		gm = gameObject.GetComponent<GameManager>();
	}
	
	// Use this for initialization
	void Start () {
		//ORIGINAL locations of ui elements
		oldScroll = SHOP_scroll.transform.position;
		oldBar = SHOP_bar.transform.position;
		oldGeneral = SHOP_general.transform.position;
	}

	public void Zoom() {
		if (Application.loadedLevel == 0)
						Application.LoadLevel (1);
		else Application.LoadLevel(0);
	}

	public void Prompt(string words) {

		prompt.transform.localScale = new Vector3(1,1,1);
		prompt.SetActive(true);
		iTween.ScaleFrom(prompt, new Vector3(0,0,0), 0.6f);
		promptLabel.text = words;

		gm.freezeActions = true;

		vignettes[1].SetActive(true);
		
		//starts at 0.5529411
		StartCoroutine(beginAnim());
		TweenAlpha.Begin(vignettes[1], 0.6f, 1);


	}

	public IEnumerator beginAnim() {
		yield return new WaitForSeconds(0.9f);
		prompt.GetComponent<Animation> ().enabled = true;
		prompt.GetComponent<Animation>().Play();
	}

	public void dismissPrompt() {
		StartCoroutine(removePrompt());

		TweenAlpha.Begin(vignettes[1], 0.6f, 0);
	}

	IEnumerator removePrompt() {
		prompt.GetComponent<Animation>().enabled = false;
		iTween.ScaleTo(prompt, new Vector3(0,0,0), 0.6f);
		yield return new WaitForSeconds(0.36f);
		gm.freezeActions = false;
		prompt.SetActive(false);
		vignettes[1].SetActive(false);
	}

	public void LoadShop() {
		gm.canScroll = false;
		SHOP_scroll.GetComponent<UIScrollView>().enabled = false;
		gm.freezeActions = true;
		SHOP_bar.SetActive(true);
		SHOP_general.SetActive(true);
		SHOP_scroll.SetActive(true);

		iTween.MoveFrom(SHOP_bar, iTween.Hash("position", new Vector3(0,-1400,0), "islocal", true, "time", 0.8f));
		iTween.MoveFrom(SHOP_general, iTween.Hash("position", new Vector3(0,-1200,0), "islocal", true, "time", 0.8f));
		//iTween.MoveFrom(SHOP_scroll, iTween.Hash("position", new Vector3(0,-1400,0), "islocal", true, "time", 0.8f));
		
		vignettes[1].SetActive(true);
		vignettes[2].SetActive(true);

		//starts at 0.5529411
		TweenAlpha.Begin(vignettes[0], 0.9f, 1);
		TweenAlpha.Begin(vignettes[1], 0.9f, 1);
		TweenAlpha.Begin(vignettes[2], 0.9f, 1);

		StartCoroutine(enableScrollView());
	}

	public void LoadLab() {
		gm.canScroll = false;
		LAB_scroll.GetComponent<UIScrollView>().enabled = false;
		gm.freezeActions = true;
		LAB_bar.SetActive(true);
		LAB_general.SetActive(true);
		LAB_scroll.SetActive(true);
		
		//iTween.MoveFrom(LAB_scroll, iTween.Hash("position", new Vector3(0,-1400,0), "islocal", true, "time", 0.8f));
		iTween.MoveFrom(LAB_bar, iTween.Hash("position", new Vector3(0,-1400,0), "islocal", true, "time", 0.8f));
		iTween.MoveFrom(LAB_general, iTween.Hash("position", new Vector3(0,-1200,0), "islocal", true, "time", 0.8f));
		
		vignettes[1].SetActive(true);
		vignettes[2].SetActive(true);
		
		//starts at 0.5529411
		TweenAlpha.Begin(vignettes[0], 0.9f, 1);
		TweenAlpha.Begin(vignettes[1], 0.9f, 1);
		TweenAlpha.Begin(vignettes[2], 0.9f, 1);
		
		StartCoroutine(enableLABScrollView());
	}


	IEnumerator enableScrollView() {
		yield return new WaitForSeconds(0.8f);
		SHOP_scroll.GetComponent<UIScrollView>().enabled = true;
	}

	IEnumerator enableLABScrollView() {
		yield return new WaitForSeconds(0.8f);
		LAB_scroll.GetComponent<UIScrollView>().enabled = true;
	}

	public void CloseShop() {
		SHOP_scroll.GetComponent<UIScrollView>().enabled = false;
		iTween.MoveTo(SHOP_scroll, iTween.Hash("position", new Vector3(0,-1400,0), "islocal", true, "time", 0.8f));
		iTween.MoveTo(SHOP_bar, iTween.Hash("position", new Vector3(0,-1400,0), "islocal", true, "time", 0.8f));
		iTween.MoveTo(SHOP_general, iTween.Hash("position", new Vector3(0,-1200,0), "islocal", true, "time", 0.8f));

		//starts at 0.5529411
		TweenAlpha.Begin(vignettes[0], 0.9f, 0.5529411f);
		TweenAlpha.Begin(vignettes[1], 0.9f, 0);
		TweenAlpha.Begin(vignettes[2], 0.9f, 0);

		StartCoroutine(ShopInactive());
	}

	public void CloseLAB() {
		LAB_scroll.GetComponent<UIScrollView>().enabled = false;
		iTween.MoveTo(LAB_scroll, iTween.Hash("position", new Vector3(0,-1400,0), "islocal", true, "time", 0.8f));
		iTween.MoveTo(LAB_bar, iTween.Hash("position", new Vector3(0,-1400,0), "islocal", true, "time", 0.8f));
		iTween.MoveTo(LAB_general, iTween.Hash("position", new Vector3(0,-1200,0), "islocal", true, "time", 0.8f));
		
		//starts at 0.5529411
		TweenAlpha.Begin(vignettes[0], 0.9f, 0.5529411f);
		TweenAlpha.Begin(vignettes[1], 0.9f, 0);
		TweenAlpha.Begin(vignettes[2], 0.9f, 0);
		
		StartCoroutine(LABInactive());
	}

	IEnumerator ShopInactive() {
		yield return new WaitForSeconds(0.8f);
		SHOP_bar.SetActive(false);
		SHOP_general.SetActive(false);
		SHOP_scroll.SetActive(false);

		SHOP_bar.transform.position = oldBar;
		SHOP_general.transform.position = oldGeneral;
		SHOP_scroll.transform.position = oldScroll;

		gm.canScroll = true;
		gm.freezeActions = false;

		yield return new WaitForSeconds(0.1f);
		vignettes[1].SetActive(false);
		vignettes[2].SetActive(false);
	}

	IEnumerator LABInactive() {
		yield return new WaitForSeconds(0.8f);
		LAB_bar.SetActive(false);
		LAB_general.SetActive(false);
		LAB_scroll.SetActive(false);
		
		LAB_bar.transform.position = oldBar;
		LAB_general.transform.position = oldGeneral;
		LAB_scroll.transform.position = oldScroll;
		
		gm.canScroll = true;
		gm.freezeActions = false;
		
		yield return new WaitForSeconds(0.1f);
		vignettes[1].SetActive(false);
		vignettes[2].SetActive(false);
	}
}
