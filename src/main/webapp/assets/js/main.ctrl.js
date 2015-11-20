fzApp.controller("mainCtrl", function ($scope, $rootScope, ItemService, TagService, uiGridConstants) {

    $scope.selectedItem = {};
    $scope.favoriteFiltered = false;
    $scope.tagFilter = '';
    $scope.allTags = TagService.query();
    $scope.alerts = [];

    $scope.itemList = ItemService.query(function(items){
            angular.forEach(items, function(item){
                item = $scope.itemDecorator(item);
            });
            $scope.addAlert('success', 'Fridgezone is online');
        },
        function(){
            $scope.addAlert('danger', 'There was a problem communicating with Fridgezone - please try logging in again');
        }
    );

    $scope.Item = function(){
      var item = {};
      item.tags = [];
        item = $scope.itemDecorator(item);
        return item;
    };

    $scope.itemDecorator = function(item){
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

    $scope.openItemDialog = function(item){
        if(!item){
            item = new $scope.Item();
        }
        $rootScope.$broadcast('openItemDialog', item);
    };

    $scope.$on('itemModified', function(event, item){
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
        if(!item.id){
            return $scope.addItem(item);
        }else{
            return $scope.updateItem(item);
        }
    };

    $scope.addItem = function(item){
        return ItemService.add(item, function(item){
            item = $scope.itemDecorator(item);
            $scope.allTags = TagService.query();
            $scope.addAlert('success', item.name + ' added');
            },
            function(error){
                $scope.addAlert('danger', 'There was a problem communicating with Fridgezone - please try logging in again');
            }
        );
    };

    $scope.updateItem = function(item){
        return ItemService.update(item, function(item){
            item = $scope.itemDecorator(item);
            $scope.allTags = TagService.query();
            $scope.addAlert('success', item.name + ' updated');
        },
            function(error){
                $scope.addAlert('danger', 'There was a problem communicating with Fridgezone - please try logging in again');
            }
        );
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


    $scope.itemGridOptions={
        data: $scope.itemList,
        columnDefs: [
            {field: 'id',  visible: false},
            {
                field: 'name',
                name: 'name',
                displayName: 'Item',
                width: 250,
                cellClass: function(grid, row) {
                    return $scope.getCellClass(row.entity);
                },
                sort: {
                    direction: uiGridConstants.ASC
                },
                enableFiltering: false
            },
            {
                field: 'stockLevel',
                displayName: 'Level',
                width: 85,
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
                        return(cellValue.search(new RegExp($scope.tagFilter, 'i')) > 0);
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

    $scope.addAlert = function(type, message){
        $scope.alerts.push({type:type, msg: message});
    };

    $scope.closeAlert = function(index) {
        $scope.alerts.splice(index, 1);
    };

    $scope.handleGridSelection = function(row){
        if(row.isSelected) {
            $scope.gridApi.selection.clearSelectedRows();
            $scope.openItemDialog(row.entity);
        }
    };

    $scope.itemGridOptions.onRegisterApi = function(gridApi){
        $scope.gridApi = gridApi;
        gridApi.selection.on.rowSelectionChanged($scope, $scope.handleGridSelection);
    };

    $scope.getCellClass = function(item){
        if(item.stockLevel === 'Low' || item.stockLevel === 'Out') {
            return 'low';
        }
        return '';
    };

});