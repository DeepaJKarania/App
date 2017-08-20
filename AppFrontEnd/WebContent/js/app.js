var app = angular.module('MyApp',['ngRoute', 'ngCookies']);

app.config(function($routeProvider)
	{
	$routeProvider
	
		.when('/',
	{
		templateUrl : 'User/home.html',
		})
		.when('/login',
	{
		templateUrl : 'User/login.html',
		controller : 'UserController'
	})
		.when('/logout',
	{
		templateUrl : 'User/home.html',
		controller : 'UserController'
	})
		.when('/register',
	{
		templateUrl : 'User/register.html',
		controller : 'UserController'
	})
		
	
});

app.run( function ($rootScope, $location, $cookieStore, $http) 
{
	 $rootScope.$on('$locationChangeStart', function (event, next, current) 
	 {
		 console.log("$locationChangeStart")
		    
		 var userPages = ['/login'];
		 
		 var currentPage = $location.path();
		 
		 var isUserPage = $.inArray(currentPage, userPages);
				 
		 var isLoggedIn = $rootScope.currentUser.username;
	        
	     console.log("isLoggedIn:" +isLoggedIn)
	     console.log("isUserPage:" +isUserPage)
	    
	 
	      
	 });
	 
	 // keep user logged in after page refresh
    $rootScope.currentUser = $cookieStore.get('currentUser') || {};
    if ($rootScope.currentUser)
    {
        $http.defaults.headers.common['Authorization'] = 'Basic' + $rootScope.currentUser; 
    }
});