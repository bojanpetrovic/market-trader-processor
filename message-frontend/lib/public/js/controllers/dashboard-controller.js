angular.module('mtpfrontend', [])

    /*
    Dashboard controller.
     */
    .controller('DashboardController', ['$scope', '$rootScope', '$http', function($scope, $rootScope, $http)
        {
            /*
            Performs log in operation.
                Parameters:
                    username - user's username
                    password - user's password
                return:
                    temporary token if credentials are ok; exception otherwise
             */
            $scope.login = function(username, password)
            {
                $http.post('/login', {username:username, password:password})

                    .success(function(data)
                    {
                        $scope.token = data;
                        $scope.loginMessage = null;
                        $scope.displayData();
                    })
                    .error(function()
                    {
                        $scope.loginMessage = "Invalid credentials.";
                    });
            }

            /*
            Displays data.
             */
            $scope.displayData = function()
            {
                // Informing user about operation
                $scope.message = "Fetching some data...";

                // Connect to socket.
                var socket = io.connect(window.location.origin, {"query" : "token=" + $scope.token});

                socket.on("error", function(err)
                {
                    $scope.token = null;
                    socket.disconnect();
                });

                // New data has been sent from processor for dashboard to display.
                socket.on("info", function(data)
                {
                    if (data.length == 0)
                    {
                        $scope.message = "No data";
                    }
                    else
                    {
                        $scope.message = null;

                        var graphData = [];
                        var graphLabels = [];

                        // Populate data for graph.
                        for (var i=0; i<data.length; ++i)
                        {
                            var conversion = data[i];
                            graphLabels.push(conversion.from + "->" + conversion.to);
                            graphData.push(conversion.count);
                        }

                        var d =
                        {
                            labels: graphLabels,

                            datasets: [
                                {
                                    label: "Number of messages",
                                    fillColor: "rgba(0,255,0,0.5)",
                                    data: graphData
                                }
                            ]
                        };

                        // Create graph
                        var ctx = $("#currencyChart").get(0).getContext("2d");
                        var chart = new Chart(ctx).Bar(d, {animation: false});
                    }

                    // Called to refresh Angular's scope while working with sockets.
                    $rootScope.$apply(function () {
                        callback.apply(socket, args);
                    });
                });
            }
        }]
    );