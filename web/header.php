<?php
	session_start();
	if(session_is_registered("email")) {
	require ('mysqlconnect.php') ;
	echo "
		<head>
		<meta charset='utf-8'>

		<title>Monta Vista DECA</title>
		<meta name='description' content='' />
		<meta name='keywords' content='' />
		<meta name='author' content='Nicholas Shen'>
		<meta name='viewport' content='width=device-width, initial-scale=1.0'>

		<link rel='shortcut icon' href='favicon.png' type='image/x-icon' />
		
		
	    <!-- ## Standard stylesheet imports and jquery imports. The majority of the styling is handled in style.css. However, columns rows and the responsiveness is handled in gumby.css. For more info on the gumby framework, please visit: http://gumbyframework.com -->
		<link rel='stylesheet' href='css/gumby.css'>
		<link rel='stylesheet' href='css/style.css'>
		<link rel='stylesheet' href='css/circle_hover.css'>

		<link rel='stylesheet' type='text/css' href='css/normalize.css' />
		<link rel='stylesheet' type='text/css' href='css/component.css' />

		<link href='http://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700,300italic,400italic,500italic,700italic|Montserrat:400,700' rel='stylesheet' type='text/css'>
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic,800,800italic' rel='stylesheet' type='text/css'>
		<link href='http://fonts.googleapis.com/css?family=Lato:400,100,100italic,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>

		<script type='text/javascript' src='js/libs/jquery-2.0.2.min.js'></script>
	    <script type='text/javascript' src='js/libs/jquery.mobile.custom.min.js'></script>
	    <script type='text/javascript' src='js/libs/jquery.easing.1.3.js'></script>
	    <script type='text/javascript' src='js/libs/jquery-1.10.1.min.js'></script>

	    <script src='http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js'></script>
		<script src='js/jquery.fittext.js'></script>

		<script src='js/main.js'></script>
		<script src='js/libs/gumby.js'></script>
		<script src='js/libs/ui/gumby.fittext.js'></script>
		<script src='js/modernizr.custom.js'></script>

		<link rel='stylesheet' href='sliding/css/slide.css' type='text/css' media='screen' />

		<script type='text/javascript' src='scripts/jquery.parallax-1.1.3.js'></script>
		<script type='text/javascript' src='scripts/jquery.localscroll-1.2.7-min.js'></script>
		<script type='text/javascript' src='scripts/jquery.scrollTo-1.4.2-min.js'></script>

  		
		<script src='sliding/js/slide.js' type='text/javascript'></script>

		<script type='text/javascript'>
			$(document).ready(function(){

				$('#top').parallax('50%', 0.1);
				$('#second').parallax('60%', 0.1);
				$('#third').parallax('60%', 0.1);

			})
		</script>

		

		<script>
	        jQuery(document).ready(function($) {
	         
	            $('.smooth').click(function(event){     
	                event.preventDefault();
	                $('html,body').animate({scrollTop:$(this.hash).offset().top - 100}, 1000);
	            });
	        });
	    </script>

	    <script>
	    	jQuery(document).ready(function($) {

	            $('.open').click(function(){
	            	
	            	if($(this).hasClass('close2')) {
	            		$('div#panel').slideUp('slow');	
	            	}
	            	else $('div#panel').slideDown('slow');
					
					$(this).toggleClass('close2');
				});	
				
				// Collapse Panel
				$('.close').click(function(){
					$('div#panel').slideUp('slow');	
				});	
	        });
	    </script>

	    <style type='text/css'>
		    .bluenav {
				color: #06569D !important;
			}

			.bluenav:hover {
				color: #0989CF !important;
			}

		</style>

	</head>

	<body id='body'>

		<header id='ha-header' class='ha-header ha-header-small'>
			<div class='ha-header-perspective'>
				<div class='ha-header-front'>
					<h1 style='top:0px' class='title'><a href='index.php'>Monta Vista DECA</a></h1>
					<nav>
						<a href='about.php'>About</a>
						<a href='competitions.php'>Competitions</a>
						<a href='community.php'>Community</a>
						<a href='events.php'>Events</a>
						<a style='color:#3498db' href='http://bbc.mvdeca.org'>BBC</a>
						<!--<a href='https://plus.google.com/photos/105429874129832309545/albums?banner=pwa'>Media</a>-->
						<a id='open' class='open smooth' href='#body'>
							My HUB
						</a>
					</nav>
				</div>	
			</div>
		</header>


		<!-- Panel -->
			<div id='toppanel'>
				<div id='panel'>
					<div class='content clearfix'>
						<div class='row'> ";

$data = mysql_query("SELECT * FROM membership WHERE stud_email='$_SESSION[email]'");
$info = mysql_fetch_array( $data );

echo "
							
							<div class='four columns'>			
								<!-- Register Form -->
									<h1 style='padding: 5px 0px 0px'> ";
									
					$input = array("Howdy", "Yello", "Hello", "Hi", "Sup", "Mahbro", "Yo", "Dawg", "Homeboy", "DECA-er", "Coolkid", "BFF", "Brosef", "Hola", "Shalom", "Namaste");
					$rand_keys = array_rand($input, 2);
					echo $input[$rand_keys[0]];
									echo " ";
									echo $info['firstname'];
									echo " ";
									echo $info['lastname'];
								
									echo ", </h1><p style='color: #FFF; font-size: 14px;'> &nbsp;(";
									echo $info['stud_email'];
									
									echo ")</p>	
									<br><br><div class='medium primary btn' style='background-color: rgb(48, 133, 214);'>
										<a href='internal.php'>Go to the Hub</a>
									</div>

									<br><br><div class='medium primary btn' style='background-color: rgb(48, 133, 214);'>
										<a href='resources.php'>Get Resources</a>
									</div>			
							</div>

							<div class='four columns'>
									<h1>Recent Updates</h1>
									<p style='color:#FFF'>
										<strong>11/30/13</strong> - SVCDC Registration is now open. Access it in the new MVDECA member hub!
									</p><br>
									<p style='color:#FFF'>
										<strong>11/29/13</strong> - The new MVDECA member hub has officially been released!
									</p>
							</div>
							<div class='three columns'>
								<div style='float:right;' class='text-right'>
									<p style='color:#FFF'> Had enough fun? <br> Come back soon! <br> We'll miss you... maybe. </p>
									<div class='medium primary btn' style='background-color: rgb(48, 133, 214)'>
										<a href='logout.php'>Logout</a>
									</div>
								</div>
							</div>
							<div class='one columns'>
								<br><a id='close' class='close' style='color:#FFF; font-size:2em;'><i class='icon-cancel'></i></a>
							</div>
						</div>
					</div>
				</div> <!-- /login -->	
			</div> <!--panel -->
";
}

else {
		echo "
		<head>
		<meta charset='utf-8'>

		<title>Monta Vista DECA</title>
		<meta name='description' content='' />
		<meta name='keywords' content='' />
		<meta name='author' content='Nicholas Shen'>
		<meta name='viewport' content='width=device-width, initial-scale=1.0'>

		<link rel='shortcut icon' href='favicon.png' type='image/x-icon' />
		
	    <!-- ## Standard stylesheet imports and jquery imports. The majority of the styling is handled in style.css. However, columns rows and the responsiveness is handled in gumby.css. For more info on the gumby framework, please visit: http://gumbyframework.com -->
		<link rel='stylesheet' href='css/gumby.css'>
		<link rel='stylesheet' href='css/style.css'>
		<link rel='stylesheet' href='css/circle_hover.css'>

		<link rel='stylesheet' type='text/css' href='css/normalize.css' />
		<link rel='stylesheet' type='text/css' href='css/component.css' />

		<link href='http://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700,300italic,400italic,500italic,700italic|Montserrat:400,700' rel='stylesheet' type='text/css'>
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,300italic,400italic,600,600italic,700,700italic,800,800italic' rel='stylesheet' type='text/css'>
		<link href='http://fonts.googleapis.com/css?family=Lato:400,100,100italic,300,300italic,400italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>

		<script type='text/javascript' src='js/libs/jquery-2.0.2.min.js'></script>
	    <script type='text/javascript' src='js/libs/jquery.mobile.custom.min.js'></script>
	    <script type='text/javascript' src='js/libs/jquery.easing.1.3.js'></script>
	    <script type='text/javascript' src='js/libs/jquery-1.10.1.min.js'></script>

	    <script src='http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js'></script>
		<script src='js/jquery.fittext.js'></script>

		<script src='js/main.js'></script>
		<script src='js/libs/gumby.js'></script>
		<script src='js/libs/ui/gumby.fittext.js'></script>
		<script src='js/modernizr.custom.js'></script>

		<link rel='stylesheet' href='sliding/css/slide.css' type='text/css' media='screen' />

		<script type='text/javascript' src='scripts/jquery.parallax-1.1.3.js'></script>
		<script type='text/javascript' src='scripts/jquery.localscroll-1.2.7-min.js'></script>
		<script type='text/javascript' src='scripts/jquery.scrollTo-1.4.2-min.js'></script>

  		
		<script src='sliding/js/slide.js' type='text/javascript'></script>

		<script type='text/javascript'>
			$(document).ready(function(){

				$('#top').parallax('50%', 0.1);
				$('#second').parallax('60%', 0.1);
				$('#third').parallax('60%', 0.1);

			})
		</script>

		<script type='text/javascript'>
			$(document).ready(function(){
				$('#login-trigger').click(function(){
					$(this).next('#login-content').slideToggle();
					$(this).toggleClass('active');					
					})
			});
		</script>


	    <script>
	    	jQuery(document).ready(function($) {

	            $('.open').click(function(){
	            	
	            	if($(this).hasClass('close2')) {
	            		$('div#panel').slideUp('slow');	
	            	}
	            	else $('div#panel').slideDown('slow');
					
					$(this).toggleClass('close2');
				});	
				
				// Collapse Panel
				$('.close').click(function(){
					$('div#panel').slideUp('slow');	
				});	
	        });
	    </script>

		<script>
	        jQuery(document).ready(function($) {
	         
	            $('.smooth').click(function(event){     
	                event.preventDefault();
	                $('html,body').animate({scrollTop:$(this.hash).offset().top - 100}, 1000);
	            });
	        });
	    </script>

	    <style type='text/css'>
		    .bluenav {
				color: #06569D !important;
			}

			.bluenav:hover {
				color: #0989CF !important;
			}

			/*@media screen and (max-width: 68em) {
				.ha-header h1 {
					font-size: 1.4em;
					font-weight: 400;
				}

				.ha-header nav {
					font-size: 90%;
				}
			}*/
		</style>

	</head>

	<body id='body'>

		<header id='ha-header' class='ha-header ha-header-small'>
			<div class='ha-header-perspective'>
				<div class='ha-header-front'>
					<h1 style='top:0px' class='title'><a href='index.php'>Monta Vista DECA</a></h1>
					<nav>
						<a href='about.php'>About</a>
						<a href='competitions.php'>Competitions</a>
						<a href='community.php'>Community</a>
						<a href='events.php'>Events</a>
						<a style='color:#3498db' href='http://bbc.mvdeca.org'>BBC</a>
						<!--<a href='https://plus.google.com/photos/105429874129832309545/albums?banner=pwa'>Media</a>-->
						<a id='open' class='open smooth' href='#body'>
							Log in
						</a>
					</nav>
				</div>	
			</div>
		</header>

		<div class='pintop' style='position:fixed; bottom:0px; top:auto; z-index:500;'>
				<div class='medium primary btn buttonhover' style='float:right; margin-right:15px;'><a href='registration.php'><span style='font-weight:400; font-size:1em; letter-spacing:3px;'> JOIN US</span>&nbsp; <i class='icon-user-add'></i></a></div>
		</div>

		<!-- Panel -->
			<div id='toppanel'>
				<div id='panel'>
					<div class='content clearfix'>
						<div class='row'>
							
							<div class='six columns'>			
								<!-- Register Form -->
								<form action='registration.php' onsubmit='return storeValues(this)'>
									<h1>Not a member yet? Sign Up!</h1>				
								<label class='grey' style='line-height:1.0em'>First Name:</label>
								<input class='field' style='height:24px; margin-top:0px;' name='first' type='text' size='23'/>
								<label class='grey' style='line-height:1.0em'>Last Name:</label><input class='field' style='height:24px; margin-top:0px;' name='last' type='text' size='23' />
								<label class='grey' style='line-height:1.0em'>Email:</label>
								<input class='field' type='email' style='height:24px; margin-top:0px;' name='email' size='23'/><br/>
									<input type='submit' name='submit' value='Register' class='medium primary btn' style='padding:0px 24px !important; font-size:1em; color:#FFF;' />
								</form>
							</div>

							<div class='five columns'>
								<!-- Login Form -->
								<form class='clearfix' action='check.php' method='post'>
									<h1>Member Login</h1>
									<label class='grey' for='log'>Email:</label>
									<input class='field' style='height:24px;' type='text' name='log' id='log' value='' size='23' />
									<label class='grey' for='pwd'>Password:</label>
									<input class='field' style='height:24px;' type='password' name='pwd' id='pwd' size='23' />
					            	<label style='font-size:1em !important;'><a style='color: rgb(40, 173, 234);' href='internal/ForgotPassword/forgotpassword.php'>&nbsp;Forgot your password?</a></label>
				        			<div class='clear'></div>
									<input type='submit' name='submit' value='Login'  class='medium primary btn' style='padding:0px 24px !important; font-size:1em; color:#FFF;' />
									
								</form>
								
							</div>
							<div class='one columns'>
								<br><a id='close' class='close' style='color:#FFF; font-size:2em;'><i class='icon-cancel'></i></a>
							</div>
						</div>
					</div>
				</div> <!-- /login -->	
			</div> <!--panel -->
";
	
}

?>		


	