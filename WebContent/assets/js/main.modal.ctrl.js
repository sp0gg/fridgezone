fzApp.controller('ItemModalCtrl', function ($scope, $rootScope, $modal, $log, Item) {

    $scope.$on('openItemDialog', function(event, item){
        console.log('received item for modal ' + angular.toJson(item));
        $scope.open(item);
    });

    $scope.open = function(item) {
        var modalInstance = $modal.open({
            templateUrl: 'itemModalTemplate',
            controller: 'ItemModalInstanceCtrl',
            resolve: {
                item: function () {
                    return angular.copy(item);
                }
            }
        });

        modalInstance.result.then(function (modalItem) {
            console.log('modal returning item ' + angular.toJson(modalItem));
            $rootScope.$broadcast('itemAdded', modalItem);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

});

fzApp.controller('ItemModalInstanceCtrl', function ($scope, $modalInstance, item) {
    $scope.stockLevelValues = [
        'Out',
        'Low',
        'Stocked',
        'Surplus'
    ];

    $scope.newItem = item;

    $scope.ok = function () {
        $modalInstance.close(item);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };


});