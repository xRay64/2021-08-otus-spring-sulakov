(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["genreAddEdit"],{c72a:function(t,e,i){"use strict";i.r(e);var n=function(){var t=this,e=t.$createElement,i=t._self._c||e;return i("div",[i("b-row",[i("b-col"),i("b-col",[i("b-form",{on:{submit:t.onSubmit}},[i("b-form-group",{directives:[{name:"show",rawName:"v-show",value:t.isEdit,expression:"isEdit"}],attrs:{id:"input-group-1",label:"Genre id:","label-for":"input-id",description:"Not editable"}},[i("b-form-input",{attrs:{id:"input-id",required:"",readonly:""},model:{value:t.form.id,callback:function(e){t.$set(t.form,"id",e)},expression:"form.id"}})],1),i("b-form-group",{attrs:{id:"input-group-2",label:"Genre name:","label-for":"input-name"}},[i("b-form-input",{attrs:{id:"input-name",required:""},model:{value:t.form.name,callback:function(e){t.$set(t.form,"name",e)},expression:"form.name"}})],1),i("b-button",{attrs:{type:"submit",variant:"primary"}},[t._v("Save")])],1)],1),i("b-col")],1)],1)},r=[],a=(i("b0c0"),i("bc3a")),o=i.n(a),s={data:function(){return{isEdit:!1,form:{id:"",name:""}}},mounted:function(){null!=this.$route.params.item&&(this.form.id=this.$route.params.item.id,this.form.name=this.$route.params.item.name,this.isEdit=!0)},methods:{onSubmit:function(t){var e=this;t.preventDefault(),this.isEdit?o.a.put("/genre/"+this.form.id,null,{params:{name:this.form.name}}).then((function(t){})).catch((function(t){e.errors.push(t)})):o.a.post("/genre",null,{params:{name:this.form.name}}).then((function(t){})).catch((function(t){e.errors.push(t)})),this.$router.push({path:"/genres"})}}},u=s,m=i("2877"),l=Object(m["a"])(u,n,r,!1,null,null,null);e["default"]=l.exports}}]);
//# sourceMappingURL=genreAddEdit.d82ddbe9.js.map