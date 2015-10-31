fzApp.controller("mainCtrl", function ($scope, $rootScope, Item, Tag, uiGridConstants) {

    $scope.selectedItem = {};
    $scope.favoriteFiltered = false;
    $scope.tagFilter = '';
    $scope.allTags = Tag.query();
    $scope.alerts = [];

    $scope.ItemConst = function(){
      var item = {};
        item.tags = [];
        item.getTagsFormatted = function(){
            var tagsFormatted = '';
            angular.forEach(this.tags, function(tag){
                tagsFormatted += ' | ' + tag.name;
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
                  tagsFormatted += ' | ' + tag.name;
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
        $scope.addAlert('success', 'Fridgezone is online');
    },
        function(data){
            $scope.addAlert('danger', 'Fridgezone is offline - please try again later');
        }
    );

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

    $scope.$on('itemModified', function(event, item){
        var returnedItem = $scope.saveItem(item);
        console.log('saved item is ' + angular.toJson(returnedItem));

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
                    tagsFormatted += ' | ' + tag.name;
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
            $scope.allTags = Tag.query();
            $scope.addAlert('success', item.name + ' added');

            },
            function(error){
                $scope.addAlert('danger', 'There was a problem communicating with Fridgezone - please try logging in again: ');
            }

        );
    };

    $scope.updateItem = function(item){
        return Item.update(item, function(item){
            item.getTagsFormatted = function(){
                var tagsFormatted = '';
                angular.forEach(this.tags, function(tag){
                    tagsFormatted += ' | ' + tag.name;
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
            $scope.allTags = Tag.query();
            $scope.addAlert('success', item.name + ' updated');
        },
            function(error){
                $scope.addAlert('danger', 'There was a problem communicating with Fridgezone - please try logging in again: ');
            }
        );
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

    $scope.addAlert = function(type, message){
        $scope.alerts.push({type:type, msg: message});
    };

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };

    $scope.itemGridOptions.onRegisterApi = function(gridApi){
        $scope.gridApi = gridApi;
        gridApi.selection.on.rowSelectionChanged($scope, $scope.handleGridSelection);
    };

});