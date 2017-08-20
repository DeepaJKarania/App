app.controller('UserController', function($scope, $location, UserService, $rootScope, $cookieStore, $http)
{
		console.log("Entering User Controller")
		var self = this;
		
		$scope.users;
		$scope.user={username:'', first_name:'', last_name:'',  gender:'', mail_id:'', password:'',  errorMsg:'', errorCode:''};
		$scope.message;
	
		
		self.registerUser=function()
		{
			console.log("Entering Register User")
			UserService.registerUser($scope.user)
			.then
			(
				function(response)
				{
					console.log("Registeration Successful "+response.status)
					$location.path("/home")
				}
			);
		};
		
		self.authenticate = function(user)
		{
			console.log("Authenticate Function");
			UserService.authenticate(user)
			.then 
			(
				function(d)
				{
					$scope.user = d;
					console.log("User ErrorCode - "+$scope.user.errorCode)
					if($scope.user.username == null)
					{
						alert("Invalid Username or Password");
						console.log("Invalid Username or Password")
						$location.path("/login");
					}
					
					else
					{
						console.log("Valid Credentials, Navigating to home page "+$scope.user.status)
						$scope.message1="Successfully Logged in as ";
						$rootScope.currentUser = 
							{
								username: $scope.user.username,
								first_name: $scope.user.first_name,
								last_name: $scope.user.last_name,
								gender: $scope.user.gender,
								mail_id: $scope.user.mail_id,
							};
						$http.defaults.headers.common['Authorization'] = 'Basic' + $rootScope.currentUser;
						$cookieStore.put('currentUser', $rootScope.currentUser)
						$location.path("/");
					}
				}, 	function(errResponse)
				{
					console.error("Error Authenticating User");
					$scope.message = "Invalid username or password.";
					$location.path("/login");
				}
			);
		};
		
		self.login= function()
		{
			console.log("Validating Login "+$scope.user);
			self.authenticate($scope.user);
		};
		
		self.logout= function()
		{
			console.log("Entering Logout Function");
			$rootScope.currentUser = {};
			$cookieStore.remove('currentUser');
			
			console.log("Calling Session Logout");
			UserService.logout()
			$location.path('/login');
		};
		
		
})