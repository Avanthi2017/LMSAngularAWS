lmsApp.config(["$routeProvider", function($routeProvider){
	return $routeProvider.when("/", {
		redirectTo: "/home"
	}).when("/home",{
		templateUrl: "welcome.html"
	}).when("/admin",{
		templateUrl: "admin.html"
	}).when("/librarian",{
		templateUrl: "librarian.html"
	}).when("/borrower",{
		templateUrl: "borrower.html"
	}).when("/viewauthors",{
		templateUrl: "viewauthors.html"
	}).when("/viewbooks",{
		templateUrl: "viewbooks.html"
	}).when("/viewlibraian",{
		templateUrl: "editbranchlibrarian.html"
	}).when("/viewborrower",{
		templateUrl: "checkin.html"
	}).when("/viewbookLoans",{
		templateUrl: "viewbookloans.html"
	}).when("/viewborrowers",{
		templateUrl: "viewborrowers.html"
	}).when("/viewbranchs",{
		templateUrl: "viewbranchs.html"
	}).when("/viewpublishers",{
		templateUrl: "viewpublishers.html"
	}).when("/viewgenres",{
		templateUrl: "viewgenres.html"
	})
}])

