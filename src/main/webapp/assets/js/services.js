/**
 * Created by sp0gg on 8/22/15.
 */
fzApp.factory("Item", function itemServiceFactory($resource) {
    return $resource('api/items/:id', {id: '@id'}, {
        query: {method: 'GET', params: {id: ''}, isArray: true}
        , add: {method: 'POST'}
        , update: {method: 'PUT'}
    });
});

fzApp.factory("Tag", function tagServiceFactory($resource){
    return $resource('api/tags');
    });