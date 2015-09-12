fzApp.controller('ItemModalCtrl', function ($scope, $rootScope, $modal, $log) {

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
    $scope.item = angular.copy(item);

    $scope.saveLabel = (function(){
        if(typeof item.id === 'undefined'){
            return 'Add';
        }else{
            return 'Update';
        }
    })();

    $scope.ok = function () {
        $modalInstance.close(item);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };


});