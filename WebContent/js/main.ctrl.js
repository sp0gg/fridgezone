angular.module('fridgezone').controller("MainController", ['$http', function($http){
    var vm = this;
    vm.title = 'NG title';
    vm.itemList = $http.get('api/items');
    ItemService.query();



    vm.itemList2 = [
        {
            title: 'Game of Thrones',
            year: 2011,
            favorite: true
        },
        {
            title: 'Walking Dead',
            year: 2010,
            favorite: false
        },
        {
            title: 'Firefly',
            year: 2002,
            favorite: true
        },
        {
            title: 'Banshee',
            year: 2013,
            favorite: true
        },
        {
            title: 'Greys Anatomy',
            year: 2005,
            favorite: false
        }
    ];
}]);