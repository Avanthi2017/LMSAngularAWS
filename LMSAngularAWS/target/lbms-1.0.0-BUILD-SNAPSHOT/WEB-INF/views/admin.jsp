<!DOCTYPE html>
<html>
<title>GCIT Library</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="css/style.css" type="text/css" media="all">
<body>

	<!-- Sidebar -->
	<div class="w3-sidebar w3-bar-block w3-animate-left w3-black w3-small"
		style="display: none; z-index: 10" id="mySidebar">

		<a href="viewbook" class="w3-bar-item w3-button " >Book</a>
		 <a href="viewgenre"class="w3-bar-item w3-button ">Genre</a>
		 <a href="viewpublisher"class="w3-bar-item w3-button ">Publisher</a> 
		 <a href="viewborrower"class="w3-bar-item w3-button ">Borrower</a> 
		 <a href="author/loadViewPage"class="w3-bar-item w3-button ">Author</a>
		 	 <a href="viewbranch"class="w3-bar-item w3-button ">Branch</a>
		 <a href="viewbookloan"class="w3-bar-item w3-button ">OverrideBookLoan</a>
		  <a href="Welcome"class="w3-bar-item w3-button ">Back</a>
	</div>

	<!-- Page Content -->
	<div class="w3-overlay w3-animate-opacity" onclick="w3_close()"
		style="cursor: pointer" id="myOverlay"></div>

	<div>
		<button class=" w3-button w3-gray w3-xxmedium w3-display-topleft" onclick="w3_open()">&#9776;</button>
	</div>
	<div ><h2 class="w3-display-middle">WELCOME ADMINISTRATOR</h2></div>

	<script>
		function w3_open() {
			document.getElementById("mySidebar").style.display = "block";
			document.getElementById("myOverlay").style.display = "block";
		}
		function w3_close() {
			document.getElementById("mySidebar").style.display = "none";
			document.getElementById("myOverlay").style.display = "none";
		}
	</script>

</body>
</html>
