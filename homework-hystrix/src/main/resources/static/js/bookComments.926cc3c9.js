(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["bookComments"],{"8c0c":function(t,e,o){"use strict";o.r(e);var n=function(){var t=this,e=t.$createElement,o=t._self._c||e;return o("div",[t._l(t.comments,(function(e){return o("b-container",{key:e.id},[o("b-row",[o("b-col"),o("b-col",{staticClass:"bg-secondary text-white text-left text-wrap shadow",attrs:{cols:"6"}},[t._v(" "+t._s(e.text)+" ")]),o("b-col")],1),o("b-row",[t._v("   ")])],1)})),o("b-row",[o("b-col"),o("b-col",[o("b-form",{on:{submit:t.onSubmit}},[o("b-form-textarea",{attrs:{id:"textarea",placeholder:"Enter new comment here...",rows:"3","max-rows":"6"},model:{value:t.newCommentText,callback:function(e){t.newCommentText=e},expression:"newCommentText"}}),o("br"),o("b-button",{attrs:{type:"submit",variant:"primary"}},[t._v("Save")])],1)],1),o("b-col")],1)],2)},m=[],s=o("bc3a"),a=o.n(s),r={data:function(){return{bookId:null,comments:null,newCommentText:null}},mounted:function(){null!=this.$route.params.bookId&&(this.bookId=this.$route.params.bookId,this.getComments(this.$route.params.bookId))},methods:{getComments:function(t){var e=this;a.a.get("/book/"+t+"/comments").then((function(t){e.comments=t.data}))},onSubmit:function(t){var e=this,o=this.bookId;t.preventDefault(),a.a.post("/book/"+o+"/comment",null,{params:{commentText:this.newCommentText}}).then((function(t){200===t.status&&(""===e.comments?e.comments=[{id:1,text:e.newCommentText}]:e.comments.push({id:e.comments.lenght+1,text:e.newCommentText}),e.newCommentText="")})).catch((function(t){e.errors.push(t)}))}}},c=r,i=o("2877"),u=Object(i["a"])(c,n,m,!1,null,null,null);e["default"]=u.exports}}]);
//# sourceMappingURL=bookComments.926cc3c9.js.map