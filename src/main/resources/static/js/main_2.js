var lordYoungApi = Vue.resource('/lord/youngLord');
var planetSave = Vue.resource('/planet/add');

Vue.component('planet-row', {
    props: ['planet'],
    template: '<div>' +
        '<div v-for="lord in lords">' +
        '<div></div>'+
        '<div>id: {{planet.id}}</div>' +
        '<div>name: {{planet.name}}</div>' +
        '</div>' +
        '</div>',
})

Vue.component('lord-list-young', {
    props: ['lords'],
    template: '<div>' +
        '<div v-for="lord in lords">' +
        '<div></div>'+
        '<div>Lord: </div>' +
        '<div>id: {{lord.id}}</div>' +
         '<div>name: {{lord.name}}</div>' +
         '<div style="padding-bottom: 15px">age: {{lord.age}}</div>' +
        '</div>' +
        '</div>',
    created: function () {
        lordYoungApi.get().then(result =>
            result.json().then(data =>
                data.lords.forEach(lord => this.lords.push(lord.lord)))
            )
    }
})

var app2 = new Vue({
    el: '#app2',
    template: '<lord-list-young :lords="lords"/>',
    data: {
        lords: []
    }
});

var deletePlanet = new Vue({
    el: '#deletePlanet',
    props: ['ruleId'],
    data: function () {
        return {
            idPlanets: ''
        }
    },
    template: '<div><input id="inputDeleteId" type="text" placeholder="Write planet" v-model="idPlanets"/> <input id ="inputDeleteButton" type="button" value="Delete Planet" v-on:click="rule"/></div>'
    ,
    methods: {
        rule: function () {
            var dataFunc = '/planet/delete/'+this.idPlanets
            dataFunc[this.idLord] = ''
            this.$http.post(dataFunc).then()
        }
    }
})


