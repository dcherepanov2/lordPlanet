
var lordApi = Vue.resource('/lord/allLoafers');
var lordSave = Vue.resource('/lord/add');
var youngLordURI = Vue.resource('/lord/add');

Vue.component('lord-row', {
    props: ['lord'],
    template: '<div>Lord: ' +
        '<div>id: {{lord.id}}</div>' +
        ' <div>name: {{lord.name}}</div>' +
        '<div style="padding-bottom: 15px">age: {{lord.age}}</div>' +
        '<div>Planets: </div>'+
        '</div><br>'
})

Vue.component('lord-add-form', {
    props: ['lords'],
    data: function () {
        return {
            name: '',
            age: ''
        }
    },
    template: '<div> <input id="inputNameLord" type="text" placeholder="Write name" v-model="name"/> <input id="inputAgeLord" type="text" placeholder="Write age" v-model="age"/> <input id="inputButtonLord" type="button" value="Save" v-on:click="save"/></div>'
    ,
    methods: {
        save: function () {
            var lord = {name: this.name, age: this.age};
            lordSave.save({}, lord).then(result => {
                result.json().then(data => {
                    this.lords.push(lord.lord)
                    this.name = ''
                    this.age = ''
                })
            })
        }
    }
})

Vue.component('lord-list', {
    props: ['lords'],
    template: '<div><lord-row v-for="lord in lords" :key="lord.id" :lord="lord"></lord-row></div>',
    created: function () {
        lordApi.get().then(result =>
            result.json().then(data =>
                data.lords.forEach(lord => this.lords.push(lord.lord)))
        )
    }
})

Vue.component('save-lord', {
    props: ['lords'],
    template: '<lord-add-form :lords="lords"/>',
    // +
    // '<div><lord-row v-for="lord in lords" :key="lord.id" :lord="lord"></lord-row></div>',
    created: function () {
        lordApi.get().then(result =>
            result.json().then(data =>
                data.lords.forEach(lord => this.lords.push(lord.lord)))
        )
    }
})

var app = new Vue({
    el: '#app',
    template: '<lord-list :lords="lords" />',
    data: {
        lords: []
    }
})

var saveLord = new Vue({
    el: '#saveLord',
    template: '<save-lord :lords="lords" />',
    data: {
        lords: []
    }
})

var rulePlanet = new Vue({
    el: '#rulePlanet',
    props: ['ruleId'],
    data: function () {
        return {
            idLord: '',
            idPlanets: ''
        }
    },
    template: '<div> <input id="inputIdLordRule" type="text" placeholder="Write id" v-model="idLord"/> <input id="inputIdPlanetsRule" type="text" placeholder="Write planet" v-model="idPlanets"/> <input id="inputButtonRule" type="button" value="Rule" v-on:click="rule"/> </div>'
    ,
    methods: {
        rule: function () {
            var dataFunc = '/lord/rule/'+this.idLord+'?planets='+this.idPlanets
            dataFunc[this.idLord] = ''
            this.$http.post(dataFunc).then()
        }
    }
})

