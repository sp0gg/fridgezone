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
            console.log("item returned: " + angular.toJson(modalItem));
            $rootScope.$broadcast('itemAdded', modalItem);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };

});

fzApp.controller('ItemModalInstanceCtrl', function ($scope, $modalInstance, item) {

    $scope.newItem = item;
    $scope.item = angular.copy(item);

    $scope.containsTag = function(item, tagName){
        if("tags" in item) {
            var favorites = item.tags.filter(function (tag) {
                return (tag.name === tagName);
            });
            return (favorites.length > 0);
        }
        return false;
    };

    $scope.favorite = (function(){
        return $scope.containsTag(item, 'favorite');
    }());

    $scope.shopping = (function(){
        return $scope.containsTag(item, 'shopping');
    }());

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

    $scope.toggleFavorite = function(){
        var favorite = $scope.favorite;
        item.tags = [];
        if(favorite){
            var tag = {name: "favorite"};
            item.tags.push(tag);
        }else{
            item.tags = undefined;
        }
    };

    $scope.toggleShopping = function(){
        var shopping = $scope.shopping;
        item.tags = [];
        if(shopping){
            var tag = {name: 'shopping'};
            item.tags.push(tag);
        }else{
            item.tags = undefined;
        }
    };

    $scope.save = function () {
        $modalInstance.close(item);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

});