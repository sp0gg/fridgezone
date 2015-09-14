fzApp.controller('ItemModalCtrl', function ($scope, $rootScope, $modal, $log) {

    $scope.$on('openItemDialog', function(event, item){
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
            $rootScope.$broadcast('itemAdded', modalItem);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

});

fzApp.controller('ItemModalInstanceCtrl', function ($scope, $modalInstance, item) {

    $scope.newItem = item;
    $scope.item = angular.copy(item);

    $scope.stockLevelValues = [
        'Surplus',
        'Stocked',
        'Low',
        'Out'
    ];

    $scope.saveLabel = (function(){
        if(typeof item.id === 'undefined'){
            return 'Add';
        }else{
            return 'Update';
        }
    }());

    $scope.save = function () {
        $modalInstance.close(item);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

});