fzApp.controller('ItemModalCtrl', function ($scope, $rootScope, $uibModal, $log) {

    $scope.$on('openItemDialog', function(event, item){
        $scope.open(item);
    });

    $scope.open = function(item) {
        var modalInstance = $uibModal.open({
            templateUrl: 'itemModalTemplate',
            controller: 'ItemModalInstanceCtrl',
            resolve: {
                item: function(){
                    return angular.copy(item);
                }
            }
        });

        modalInstance.result.then(function (modalItem) {
            $rootScope.$broadcast('itemModified', modalItem);
        }, function () {
            $log.info('Modal dismissed at: ' + new Date());
        });
    };
});

fzApp.controller('ItemModalInstanceCtrl', function ($scope, $modalInstance, item, TagService) {

    $scope.modalItem = item;
    $scope.modalTag = {};
    $scope.item = angular.copy(item);
    $scope.allTags = TagService.query();


    $scope.stockLevelValues = [
        'Surplus',
        'Stocked',
        'Low',
        'Out'
    ];

    $scope.containsTag = function(item, tagName){
        if('tags' in item) {
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

    $scope.saveLabel = (function(){
        if(typeof item.id === 'undefined'){
            return 'Add';
        }else{
            return 'Update';
        }
    }());

    $scope.filterForCustomTags = function(tag){
        return (tag.name !== 'favorite' && tag.name !== 'shopping')
    };

    $scope.addTag = function(tagName){
        var tag = {name: tagName};
        item.tags.push(tag);
    };

    $scope.removeTag = function(modalTag){
        if("tags" in item) {
            item.tags = item.tags.filter(function (tag) {
                return tag.name !== modalTag.name;
            })
        }
    };

    $scope.addModalTag = function(){
        var modalTag = angular.copy($scope.modalTag);
        $scope.addTag(modalTag.name);
        $scope.modalTag = {};
    };

    $scope.toggleFavorite = function(){
        if($scope.favorite){
            $scope.addTag('favorite');
        }else{
            if("tags" in item) {
                item.tags = item.tags.filter(function (tag) {
                    return tag.name !== 'favorite';
                })
            }
        }
    };

    $scope.toggleShopping = function(){
        if($scope.shopping){
            $scope.addTag('shopping');
        }else{
            if("tags" in item) {
                item.tags = item.tags.filter(function (tag) {
                    return tag.name !== 'shopping';
                })
            }
        }
    };

    $scope.save = function () {
        $modalInstance.close(item);
    };

    $scope.cancel = function () {
        $modalInstance.dismiss('cancel');
    };

});