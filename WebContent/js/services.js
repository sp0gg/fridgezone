/**
 * Created by sp0gg on 8/22/15.
 */
var ItemService = ['$resource', function($resource) {
    return $resource('/api/items');
}];