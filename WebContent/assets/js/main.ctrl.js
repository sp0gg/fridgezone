fzApp.controller("mainCtrl", function ($scope, $rootScope, Item, uiGridConstants) {

    $scope.selectedItem = {};
    $scope.favoriteFiltered = false;
    $scope.tagFilter = '';

    $scope.ItemConst = function(){
      var item = {};
        item.tags = [];
        item.getTagsFormatted = function(){
            var tagsFormatted = '';
            angular.forEach(this.tags, function(tag){
                tagsFormatted += ' ' + tag.name;
            });
            return tagsFormatted;
        };
        item.getCustomTags = function() {
            var customTags = [];
            angular.forEach(this.tags, function (tag) {
                if (tag.name !== 'favorite' && tag.name !== 'shopping') {
                    customTags.push(tag);
                }
            });
            return customTags;
        };
        return item;
    };

    $scope.itemList = Item.query(function(items){
        angular.forEach(items, function(item){
            item.getTagsFormatted = function(){
                var tagsFormatted = '';
                angular.forEach(this.tags, function(tag){
                  tagsFormatted += ' ' + tag.name;
                });
                return tagsFormatted;
            };
            item.getCustomTags = function() {
                var customTags = [];
                angular.forEach(this.tags, function (tag) {
                    if (tag.name !== 'favorite' && tag.name !== 'shopping') {
                        customTags.push(tag);
                    }
                });
                return customTags;
            };
        });
    });

    $scope.handleGridSelection = function(row){
        if(row.isSelected) {
            $scope.gridApi.selection.clearSelectedRows();
            $scope.openItemDialog(row.entity);
        }
    };

    $scope.openItemDialog = function(item){
        if(typeof item === 'undefined'){
            item = new $scope.ItemConst();
        }
        $rootScope.$broadcast('openItemDialog', item);
    };

    $scope.$on('itemAdded', function(event, item){
        var returnedItem = $scope.saveItem(item);

        var oldItem = $scope.itemList.filter(function(listItem){
            return listItem.id === returnedItem.id;
        })[0];
        var index = $scope.itemList.indexOf(oldItem);
        if(index >= 0) {
            $scope.itemList[index] = returnedItem;
        }else{
            $scope.itemList.push(returnedItem);
        }
    });

    $scope.saveItem = function(item){
        if(typeof item.id === 'undefined'){
            return $scope.addItem(item);
        }else{
            return $scope.updateItem(item);
        }
    };

    $scope.addItem = function(item){
        return Item.add(item, function(item){
            item.getTagsFormatted = function(){
                var tagsFormatted = '';
                angular.forEach(this.tags, function(tag){
                    tagsFormatted += ' ' + tag.name;
                });
                return tagsFormatted;
            };
            item.getCustomTags = function() {
                var customTags = [];
                angular.forEach(this.tags, function (tag) {
                    if (tag.name !== 'favorite' && tag.name !== 'shopping') {
                        customTags.push(tag);
                    }
                });
                return customTags;
            };
        });
    };

    $scope.updateItem = function(item){
        return Item.update(item, function(item){
            item.getTagsFormatted = function(){
                var tagsFormatted = '';
                angular.forEach(this.tags, function(tag){
                    tagsFormatted += ' ' + tag.name;
                });
                return tagsFormatted;
            };
            item.getCustomTags = function() {
                var customTags = [];
                angular.forEach(this.tags, function (tag) {
                    if (tag.name !== 'favorite' && tag.name !== 'shopping') {
                        customTags.push(tag);
                    }
                });
                return customTags;
            };
        });
    };

    $scope.getCellClass = function(item){
        if(item.stockLevel === 'Low' || item.stockLevel === 'Out') {
            return 'low';
        }
        return '';
    };

    $scope.itemGridOptions={
        data: $scope.itemList,
        columnDefs: [
            {field: 'id',  visible: false},
            {field: 'name', name: 'name', displayName: 'Item',
                cellClass: function(grid, row) {
                    return $scope.getCellClass(row.entity);
                },
                sort: {
                    direction: uiGridConstants.ASC
                },
                enableFiltering: false
            },
            {field: 'stockLevel',
                cellClass: function(grid, row) {
                    return $scope.getCellClass(row.entity);
                },
                enableFiltering: false
            },
            {
                field: 'getTagsFormatted()',
                displayName: 'Tags',
                filter: {
                    condition: function(searchTerm, cellValue){
                        return((cellValue.indexOf($scope.tagFilter) > 0));
                    },
                    noTerm: true

                }
            }
        ],
        disableCancelFilterButton: true,
        enableSelectAll: false,
        enableColumnMenus: false,
        multiSelect: false,
        enableFiltering: false

    };

    $scope.filterTag = function(tag){
        $scope.tagFilter = tag;

        if(tag !== ''){
            $scope.itemGridOptions.enableFiltering = true;
        }else{
            $scope.itemGridOptions.enableFiltering = false;
        }
        $scope.gridApi.grid.refresh();
    };

    //CP
    $scope.itemGridOptions.onRegisterApi = function(gridApi){
        $scope.gridApi = gridApi;
        gridApi.selection.on.rowSelectionChanged($scope, $scope.handleGridSelection);
    };

});