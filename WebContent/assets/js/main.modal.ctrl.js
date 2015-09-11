fzApp.controller('ItemModalCtrl', function ($scope, $modal, $log, Item) {

    $scope.open = function () {
        var modalInstance = $modal.open({
            templateUrl: 'itemModalTemplate',
            controller: 'ItemModalInstanceCtrl',
            resolve: {
                item: function () {
                    return angular.copy($scope.selectedItem);
                }
            }
        });

        modalInstance.result.then(function (modalItem) {
            $scope.saveItem(modalItem);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

    $scope.saveItem = function(item){
        if(typeof item.id === 'undefined'){
            $scope.addItem(item);
        }else{
            $scope.updateItem(item);
        }
    }

    $scope.addItem = function (item) {
        var addedItem = Item.add(item);
        $scope.itemList.push(addedItem);
        $scope.newItem = {};
    }

    $scope.updateItem = function (item) {
        var index = $scope.itemList.indexOf($scope.oldItem);
        var updatedItem = Item.update(item);
        $scope.itemList[index] = updatedItem;
        $scope.setSaveDialog({}, 'add');
        return updatedItem;
    }

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