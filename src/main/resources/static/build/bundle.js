var app=function(){"use strict";function t(){}function e(t){return t()}function n(){return Object.create(null)}function l(t){t.forEach(e)}function o(t){return"function"==typeof t}function r(t,e){return t!=t?e==e:t!==e||t&&"object"==typeof t||"function"==typeof t}let s,c;function a(t,e){return s||(s=document.createElement("a")),s.href=e,t===s.href}function i(e,n,l){e.$$.on_destroy.push(function(e,...n){if(null==e)return t;const l=e.subscribe(...n);return l.unsubscribe?()=>l.unsubscribe():l}(n,l))}function u(t,e){t.appendChild(e)}function m(t,e,n){t.insertBefore(e,n||null)}function f(t){t.parentNode&&t.parentNode.removeChild(t)}function d(t,e){for(let n=0;n<t.length;n+=1)t[n]&&t[n].d(e)}function p(t){return document.createElement(t)}function h(t){return document.createTextNode(t)}function g(){return h(" ")}function v(){return h("")}function $(t,e,n,l){return t.addEventListener(e,n,l),()=>t.removeEventListener(e,n,l)}function y(t,e,n){null==n?t.removeAttribute(e):t.getAttribute(e)!==n&&t.setAttribute(e,n)}function _(t,e){e=""+e,t.data!==e&&(t.data=e)}function b(t,e,n,l){null==n?t.style.removeProperty(e):t.style.setProperty(e,n,l?"important":"")}function k(t,e,n){for(let n=0;n<t.options.length;n+=1){const l=t.options[n];if(l.__value===e)return void(l.selected=!0)}n&&void 0===e||(t.selectedIndex=-1)}function w(t){c=t}function x(t){(function(){if(!c)throw new Error("Function called outside component initialization");return c})().$$.on_mount.push(t)}const j=[],z=[];let E=[];const S=[],N=Promise.resolve();let L=!1;function C(t){E.push(t)}const M=new Set;let T=0;function K(){if(0!==T)return;const t=c;do{try{for(;T<j.length;){const t=j[T];T++,w(t),O(t.$$)}}catch(t){throw j.length=0,T=0,t}for(w(null),j.length=0,T=0;z.length;)z.pop()();for(let t=0;t<E.length;t+=1){const e=E[t];M.has(e)||(M.add(e),e())}E.length=0}while(j.length);for(;S.length;)S.pop()();L=!1,M.clear(),w(t)}function O(t){if(null!==t.fragment){t.update(),l(t.before_update);const e=t.dirty;t.dirty=[-1],t.fragment&&t.fragment.p(t.ctx,e),t.after_update.forEach(C)}}const H=new Set;let I;function F(){I={r:0,c:[],p:I}}function P(){I.r||l(I.c),I=I.p}function B(t,e){t&&t.i&&(H.delete(t),t.i(e))}function D(t,e,n,l){if(t&&t.o){if(H.has(t))return;H.add(t),I.c.push((()=>{H.delete(t),l&&(n&&t.d(1),l())})),t.o(e)}else l&&l()}function J(t){t&&t.c()}function q(t,n,r,s){const{fragment:c,after_update:a}=t.$$;c&&c.m(n,r),s||C((()=>{const n=t.$$.on_mount.map(e).filter(o);t.$$.on_destroy?t.$$.on_destroy.push(...n):l(n),t.$$.on_mount=[]})),a.forEach(C)}function A(t,e){const n=t.$$;null!==n.fragment&&(!function(t){const e=[],n=[];E.forEach((l=>-1===t.indexOf(l)?e.push(l):n.push(l))),n.forEach((t=>t())),E=e}(n.after_update),l(n.on_destroy),n.fragment&&n.fragment.d(e),n.on_destroy=n.fragment=null,n.ctx=[])}function U(t,e){-1===t.$$.dirty[0]&&(j.push(t),L||(L=!0,N.then(K)),t.$$.dirty.fill(0)),t.$$.dirty[e/31|0]|=1<<e%31}function G(e,o,r,s,a,i,u,m=[-1]){const d=c;w(e);const p=e.$$={fragment:null,ctx:[],props:i,update:t,not_equal:a,bound:n(),on_mount:[],on_destroy:[],on_disconnect:[],before_update:[],after_update:[],context:new Map(o.context||(d?d.$$.context:[])),callbacks:n(),dirty:m,skip_bound:!1,root:o.target||d.$$.root};u&&u(p.root);let h=!1;if(p.ctx=r?r(e,o.props||{},((t,n,...l)=>{const o=l.length?l[0]:n;return p.ctx&&a(p.ctx[t],p.ctx[t]=o)&&(!p.skip_bound&&p.bound[t]&&p.bound[t](o),h&&U(e,t)),n})):[],p.update(),h=!0,l(p.before_update),p.fragment=!!s&&s(p.ctx),o.target){if(o.hydrate){const t=function(t){return Array.from(t.childNodes)}(o.target);p.fragment&&p.fragment.l(t),t.forEach(f)}else p.fragment&&p.fragment.c();o.intro&&B(e.$$.fragment),q(e,o.target,o.anchor,o.customElement),K()}w(d)}class R{$destroy(){A(this,1),this.$destroy=t}$on(e,n){if(!o(n))return t;const l=this.$$.callbacks[e]||(this.$$.callbacks[e]=[]);return l.push(n),()=>{const t=l.indexOf(n);-1!==t&&l.splice(t,1)}}$set(t){var e;this.$$set&&(e=t,0!==Object.keys(e).length)&&(this.$$.skip_bound=!0,this.$$set(t),this.$$.skip_bound=!1)}}function W(e){let n;return{c(){n=p("nav"),n.innerHTML='<a href="/"><div class="logo svelte-e6tums"><img src="/assets/logo.png" alt="J3L+ Logo" class="svelte-e6tums"/> J3L+ SMP S2</div></a> \n\t<div class="menu svelte-e6tums"><a href="/" class="svelte-e6tums">Home</a> \n\t\t<a href="/players" class="svelte-e6tums">Players</a> \n\t\t<a href="/leaderboards" class="svelte-e6tums">Leaderboards</a> \n\t\t<a href="/backups" class="svelte-e6tums">Backups</a> \n\t\t<a href="/map" class="svelte-e6tums">World Map</a> \n\t\t<a href="https://github.com/Jaden-Unruh/minecraft-stats-backend" target="_blank" class="svelte-e6tums">GitHub</a></div>',y(n,"class","svelte-e6tums")},m(t,e){m(t,n,e)},p:t,i:t,o:t,d(t){t&&f(n)}}}class Q extends R{constructor(t){super(),G(this,t,null,W,r,{})}}function V(e){let n;return{c(){n=p("footer"),n.innerHTML='2024 <a href="https://linktr.ee/JadenUnruh" class="svelte-19ymqmf">Jaden Unruh</a>, J³L+ SMP',y(n,"class","svelte-19ymqmf")},m(t,e){m(t,n,e)},p:t,i:t,o:t,d(t){t&&f(n)}}}class X extends R{constructor(t){super(),G(this,t,null,V,r,{})}}function Y(t,e,n){const l=t.slice();return l[1]=e[n],l}function Z(e){let n,l;return{c(){n=p("img"),a(n.src,l=e[1])||y(n,"src",l),y(n,"alt","Server screenshot"),y(n,"class","svelte-5d8wp5")},m(t,e){m(t,n,e)},p:t,d(t){t&&f(n)}}}function tt(e){let n,l=e[0],o=[];for(let t=0;t<l.length;t+=1)o[t]=Z(Y(e,l,t));return{c(){n=p("div");for(let t=0;t<o.length;t+=1)o[t].c();y(n,"class","screenshots svelte-5d8wp5")},m(t,e){m(t,n,e);for(let t=0;t<o.length;t+=1)o[t]&&o[t].m(n,null)},p(t,[e]){if(1&e){let r;for(l=t[0],r=0;r<l.length;r+=1){const s=Y(t,l,r);o[r]?o[r].p(s,e):(o[r]=Z(s),o[r].c(),o[r].m(n,null))}for(;r<o.length;r+=1)o[r].d(1);o.length=l.length}},i:t,o:t,d(t){t&&f(n),d(o,t)}}}function et(t){return[["/assets/screenshots/screen1.png","/assets/screenshots/screen2.png","/assets/screenshots/screen3.png"]]}class nt extends R{constructor(t){super(),G(this,t,et,tt,r,{})}}function lt(t,e,n){const l=t.slice();return l[4]=e[n].uuid,l[5]=e[n].name,l}function ot(t){let e,n=t[0],l=[];for(let e=0;e<n.length;e+=1)l[e]=at(lt(t,n,e));return{c(){for(let t=0;t<l.length;t+=1)l[t].c();e=v()},m(t,n){for(let e=0;e<l.length;e+=1)l[e]&&l[e].m(t,n);m(t,e,n)},p(t,o){if(1&o){let r;for(n=t[0],r=0;r<n.length;r+=1){const s=lt(t,n,r);l[r]?l[r].p(s,o):(l[r]=at(s),l[r].c(),l[r].m(e.parentNode,e))}for(;r<l.length;r+=1)l[r].d(1);l.length=n.length}},d(t){d(l,t),t&&f(e)}}}function rt(e){let n;return{c(){n=p("p"),n.textContent="No players found."},m(t,e){m(t,n,e)},p:t,d(t){t&&f(n)}}}function st(t){let e,n,l;return{c(){e=p("p"),n=h("Error loading players: "),l=h(t[2])},m(t,o){m(t,e,o),u(e,n),u(e,l)},p(t,e){4&e&&_(l,t[2])},d(t){t&&f(e)}}}function ct(e){let n;return{c(){n=p("p"),n.textContent="Loading players..."},m(t,e){m(t,n,e)},p:t,d(t){t&&f(n)}}}function at(t){let e,n,l,o,r,s,c,a,i,d,v,$,b,k,w,x,j,z,E,S,N=t[5]+"",L=t[4]+"";return{c(){e=p("div"),n=p("span"),l=p("strong"),l.textContent="Name:",o=g(),r=h(N),s=h("; "),c=p("strong"),c.textContent="UUID:",a=g(),i=h(L),d=p("br"),v=g(),$=p("span"),b=p("a"),k=h("view player's stats"),x=g(),j=p("a"),z=h("view player's inventory"),S=g(),y(b,"href",w=`/stats/${t[5]}`),y(j,"href",E=`/inventory/${t[5]}`),y(e,"class","player-item svelte-r91h4q")},m(t,f){m(t,e,f),u(e,n),u(n,l),u(n,o),u(n,r),u(n,s),u(n,c),u(n,a),u(n,i),u(e,d),u(e,v),u(e,$),u($,b),u(b,k),u($,x),u($,j),u(j,z),u(e,S)},p(t,e){1&e&&N!==(N=t[5]+"")&&_(r,N),1&e&&L!==(L=t[4]+"")&&_(i,L),1&e&&w!==(w=`/stats/${t[5]}`)&&y(b,"href",w),1&e&&E!==(E=`/inventory/${t[5]}`)&&y(j,"href",E)},d(t){t&&f(e)}}}function it(e){let n;function l(t,e){return t[1]?ct:t[2]?st:0===t[0].length?rt:ot}let o=l(e),r=o(e);return{c(){n=p("div"),r.c(),y(n,"class","player-list svelte-r91h4q")},m(t,e){m(t,n,e),r.m(n,null)},p(t,[e]){o===(o=l(t))&&r?r.p(t,e):(r.d(1),r=o(t),r&&(r.c(),r.m(n,null)))},i:t,o:t,d(t){t&&f(n),r.d()}}}function ut(t,e,n){let l=[],o=!0,r=null;return x((async()=>{try{const t=await fetch("/api/players");if(!t.ok)throw new Error(`Error: ${t.statusText}`);const e=await t.json();n(0,l=Object.entries(e).map((([t,e])=>({uuid:t,name:e}))))}catch(t){n(2,r=t.message)}finally{n(1,o=!1)}})),[l,o,r]}class mt extends R{constructor(t){super(),G(this,t,ut,it,r,{})}}function ft(t,e,n){const l=t.slice();return l[4]=e[n],l}function dt(t){let e,n=t[0],l=[];for(let e=0;e<n.length;e+=1)l[e]=gt(ft(t,n,e));return{c(){for(let t=0;t<l.length;t+=1)l[t].c();e=v()},m(t,n){for(let e=0;e<l.length;e+=1)l[e]&&l[e].m(t,n);m(t,e,n)},p(t,o){if(1&o){let r;for(n=t[0],r=0;r<n.length;r+=1){const s=ft(t,n,r);l[r]?l[r].p(s,o):(l[r]=gt(s),l[r].c(),l[r].m(e.parentNode,e))}for(;r<l.length;r+=1)l[r].d(1);l.length=n.length}},d(t){d(l,t),t&&f(e)}}}function pt(t){let e,n,l;return{c(){e=p("p"),n=h("Error loading backups: "),l=h(t[2])},m(t,o){m(t,e,o),u(e,n),u(e,l)},p(t,e){4&e&&_(l,t[2])},d(t){t&&f(e)}}}function ht(e){let n;return{c(){n=p("p"),n.textContent="Loading backups..."},m(t,e){m(t,n,e)},p:t,d(t){t&&f(n)}}}function gt(t){let e,n,l,o,r,s,c,a,i,d,v,$,b,k=t[4].Date+"",w=t[4].Time+"",x=t[4].Size+"";return{c(){e=p("div"),n=p("span"),l=h("On "),o=h(k),r=h(" at "),s=h(w),c=h(": "),a=p("a"),i=h("Google Drive🔗"),v=h("; "),$=h(x),b=g(),y(a,"href",d=t[4].Link),y(e,"class","backup-item svelte-1rb5doj")},m(t,f){m(t,e,f),u(e,n),u(n,l),u(n,o),u(n,r),u(n,s),u(n,c),u(n,a),u(a,i),u(n,v),u(n,$),u(e,b)},p(t,e){1&e&&k!==(k=t[4].Date+"")&&_(o,k),1&e&&w!==(w=t[4].Time+"")&&_(s,w),1&e&&d!==(d=t[4].Link)&&y(a,"href",d),1&e&&x!==(x=t[4].Size+"")&&_($,x)},d(t){t&&f(e)}}}function vt(e){let n,l,o;function r(t,e){return t[1]?ht:t[2]?pt:dt}let s=r(e),c=s(e);return{c(){n=p("p"),n.innerHTML="Backups are currently run manually, and this list will be updated each time I make a new one.<br/>In the future, this may be automated.",l=g(),o=p("div"),c.c(),y(o,"class","backup-list svelte-1rb5doj")},m(t,e){m(t,n,e),m(t,l,e),m(t,o,e),c.m(o,null)},p(t,[e]){s===(s=r(t))&&c?c.p(t,e):(c.d(1),c=s(t),c&&(c.c(),c.m(o,null)))},i:t,o:t,d(t){t&&f(n),t&&f(l),t&&f(o),c.d()}}}function $t(t,e,n){let l=[],o=!0,r=null;return x((async()=>{try{const t=await fetch("/api/backups");if(!t.ok)throw new Error(`Error: ${resopnse.statusText}`);n(0,l=await t.json())}catch(t){n(2,r=t.message)}finally{n(1,o=!1)}})),[l,o,r]}class yt extends R{constructor(t){super(),G(this,t,$t,vt,r,{})}}function _t(e){let n,l;return{c(){n=p("iframe"),a(n.src,l="/api/map/index.html")||y(n,"src","/api/map/index.html"),y(n,"class","iframe-container svelte-afnwjy"),y(n,"title","J3L+S2 World Map")},m(t,e){m(t,n,e)},p:t,i:t,o:t,d(t){t&&f(n)}}}let bt=class extends R{constructor(t){super(),G(this,t,null,_t,r,{})}};function kt(t,e,n){const l=t.slice();return l[15]=e[n].name,l[28]=e[n].killed,l[29]=e[n].killed_by,l[25]=e[n].total,l}function wt(t,e,n){const l=t.slice();return l[15]=e[n].name,l[19]=e[n].broken,l[20]=e[n].used,l[21]=e[n].dropped,l[22]=e[n].crafted,l[23]=e[n].mined,l[24]=e[n].picked_up,l[25]=e[n].total,l}function xt(t,e,n){const l=t.slice();return l[15]=e[n].name,l[16]=e[n].value,l}function jt(t){let e,n,l,o,r,s,c,a,i,d,b,w,x;function j(t,e){return"general"===t[1]?Lt:"items"===t[1]?Nt:"mobs"===t[1]?St:void 0}let z=j(t),E=z&&z(t);return{c(){e=p("h2"),n=h(t[0]),l=h("'s Stats"),o=g(),r=p("div"),s=p("select"),c=p("option"),c.textContent="General Stats",a=p("option"),a.textContent="Item Stats",i=p("option"),i.textContent="Mob Stats",d=g(),E&&E.c(),b=v(),c.__value="general",c.value=c.__value,a.__value="items",a.value=a.__value,i.__value="mobs",i.value=i.__value,void 0===t[1]&&C((()=>t[7].call(s))),y(r,"class","dropdown svelte-1c42i8z")},m(f,p){m(f,e,p),u(e,n),u(e,l),m(f,o,p),m(f,r,p),u(r,s),u(s,c),u(s,a),u(s,i),k(s,t[1],!0),m(f,d,p),E&&E.m(f,p),m(f,b,p),w||(x=$(s,"change",t[7]),w=!0)},p(t,e){1&e[0]&&_(n,t[0]),2&e[0]&&k(s,t[1]),z===(z=j(t))&&E?E.p(t,e):(E&&E.d(1),E=z&&z(t),E&&(E.c(),E.m(b.parentNode,b)))},d(t){t&&f(e),t&&f(o),t&&f(r),t&&f(d),E&&E.d(t),t&&f(b),w=!1,x()}}}function zt(t){let e,n,l;return{c(){e=p("p"),n=h("Error: "),l=h(t[3])},m(t,o){m(t,e,o),u(e,n),u(e,l)},p(t,e){8&e[0]&&_(l,t[3])},d(t){t&&f(e)}}}function Et(t){let e,n,l,o;return{c(){e=p("p"),n=h("Loading stats for "),l=h(t[0]),o=h("...")},m(t,r){m(t,e,r),u(e,n),u(e,l),u(e,o)},p(t,e){1&e[0]&&_(l,t[0])},d(t){t&&f(e)}}}function St(t){let e,n,l,o,r,s,c=t[6],a=[];for(let e=0;e<c.length;e+=1)a[e]=Ct(kt(t,c,e));return{c(){e=p("h2"),e.textContent="Mob Statistics",n=g(),l=p("table"),o=p("thead"),o.innerHTML='<tr><th class="svelte-1c42i8z">Mob</th> \n\t\t\t\t\t\t<th class="svelte-1c42i8z">Killed</th> \n\t\t\t\t\t\t<th class="svelte-1c42i8z">Killed By</th> \n\t\t\t\t\t\t<th class="svelte-1c42i8z">Total</th></tr>',r=g(),s=p("tbody");for(let t=0;t<a.length;t+=1)a[t].c();y(l,"class","stats-table svelte-1c42i8z")},m(t,c){m(t,e,c),m(t,n,c),m(t,l,c),u(l,o),u(l,r),u(l,s);for(let t=0;t<a.length;t+=1)a[t]&&a[t].m(s,null)},p(t,e){if(64&e[0]){let n;for(c=t[6],n=0;n<c.length;n+=1){const l=kt(t,c,n);a[n]?a[n].p(l,e):(a[n]=Ct(l),a[n].c(),a[n].m(s,null))}for(;n<a.length;n+=1)a[n].d(1);a.length=c.length}},d(t){t&&f(e),t&&f(n),t&&f(l),d(a,t)}}}function Nt(t){let e,n,l,o,r,s,c=t[5],a=[];for(let e=0;e<c.length;e+=1)a[e]=Mt(wt(t,c,e));return{c(){e=p("h2"),e.textContent="Item Statistics",n=g(),l=p("table"),o=p("thead"),o.innerHTML='<tr><th class="svelte-1c42i8z">Item</th> \n\t\t\t\t\t\t<th class="svelte-1c42i8z">Broken</th> \n\t\t\t\t\t\t<th class="svelte-1c42i8z">Used</th> \n\t\t\t\t\t\t<th class="svelte-1c42i8z">Dropped</th> \n\t\t\t\t\t\t<th class="svelte-1c42i8z">Crafted</th> \n\t\t\t\t\t\t<th class="svelte-1c42i8z">Mined</th> \n\t\t\t\t\t\t<th class="svelte-1c42i8z">Picked up</th> \n\t\t\t\t\t\t<th class="svelte-1c42i8z">Total</th></tr>',r=g(),s=p("tbody");for(let t=0;t<a.length;t+=1)a[t].c();y(l,"class","stats-table svelte-1c42i8z")},m(t,c){m(t,e,c),m(t,n,c),m(t,l,c),u(l,o),u(l,r),u(l,s);for(let t=0;t<a.length;t+=1)a[t]&&a[t].m(s,null)},p(t,e){if(32&e[0]){let n;for(c=t[5],n=0;n<c.length;n+=1){const l=wt(t,c,n);a[n]?a[n].p(l,e):(a[n]=Mt(l),a[n].c(),a[n].m(s,null))}for(;n<a.length;n+=1)a[n].d(1);a.length=c.length}},d(t){t&&f(e),t&&f(n),t&&f(l),d(a,t)}}}function Lt(t){let e,n,l,o=t[4],r=[];for(let e=0;e<o.length;e+=1)r[e]=Tt(xt(t,o,e));return{c(){e=p("h2"),e.textContent="General Statistics",n=g();for(let t=0;t<r.length;t+=1)r[t].c();l=v()},m(t,o){m(t,e,o),m(t,n,o);for(let e=0;e<r.length;e+=1)r[e]&&r[e].m(t,o);m(t,l,o)},p(t,e){if(16&e[0]){let n;for(o=t[4],n=0;n<o.length;n+=1){const s=xt(t,o,n);r[n]?r[n].p(s,e):(r[n]=Tt(s),r[n].c(),r[n].m(l.parentNode,l))}for(;n<r.length;n+=1)r[n].d(1);r.length=o.length}},d(t){t&&f(e),t&&f(n),d(r,t),t&&f(l)}}}function Ct(t){let e,n,l,o,r,s,c,a,i,d,v,$,b,k=t[15]+"",w=t[28]+"",x=t[29]+"",j=t[25]+"";return{c(){e=p("tr"),n=p("td"),l=h(k),o=g(),r=p("td"),s=h(w),c=g(),a=p("td"),i=h(x),d=g(),v=p("td"),$=h(j),b=g(),y(n,"class","svelte-1c42i8z"),y(r,"class","svelte-1c42i8z"),y(a,"class","svelte-1c42i8z"),y(v,"class","svelte-1c42i8z")},m(t,f){m(t,e,f),u(e,n),u(n,l),u(e,o),u(e,r),u(r,s),u(e,c),u(e,a),u(a,i),u(e,d),u(e,v),u(v,$),u(e,b)},p(t,e){64&e[0]&&k!==(k=t[15]+"")&&_(l,k),64&e[0]&&w!==(w=t[28]+"")&&_(s,w),64&e[0]&&x!==(x=t[29]+"")&&_(i,x),64&e[0]&&j!==(j=t[25]+"")&&_($,j)},d(t){t&&f(e)}}}function Mt(t){let e,n,l,o,r,s,c,a,i,d,v,$,b,k,w,x,j,z,E,S,N,L,C,M,T,K=t[15]+"",O=t[19]+"",H=t[20]+"",I=t[21]+"",F=t[22]+"",P=t[23]+"",B=t[24]+"",D=t[25]+"";return{c(){e=p("tr"),n=p("td"),l=h(K),o=g(),r=p("td"),s=h(O),c=g(),a=p("td"),i=h(H),d=g(),v=p("td"),$=h(I),b=g(),k=p("td"),w=h(F),x=g(),j=p("td"),z=h(P),E=g(),S=p("td"),N=h(B),L=g(),C=p("td"),M=h(D),T=g(),y(n,"class","svelte-1c42i8z"),y(r,"class","svelte-1c42i8z"),y(a,"class","svelte-1c42i8z"),y(v,"class","svelte-1c42i8z"),y(k,"class","svelte-1c42i8z"),y(j,"class","svelte-1c42i8z"),y(S,"class","svelte-1c42i8z"),y(C,"class","svelte-1c42i8z")},m(t,f){m(t,e,f),u(e,n),u(n,l),u(e,o),u(e,r),u(r,s),u(e,c),u(e,a),u(a,i),u(e,d),u(e,v),u(v,$),u(e,b),u(e,k),u(k,w),u(e,x),u(e,j),u(j,z),u(e,E),u(e,S),u(S,N),u(e,L),u(e,C),u(C,M),u(e,T)},p(t,e){32&e[0]&&K!==(K=t[15]+"")&&_(l,K),32&e[0]&&O!==(O=t[19]+"")&&_(s,O),32&e[0]&&H!==(H=t[20]+"")&&_(i,H),32&e[0]&&I!==(I=t[21]+"")&&_($,I),32&e[0]&&F!==(F=t[22]+"")&&_(w,F),32&e[0]&&P!==(P=t[23]+"")&&_(z,P),32&e[0]&&B!==(B=t[24]+"")&&_(N,B),32&e[0]&&D!==(D=t[25]+"")&&_(M,D)},d(t){t&&f(e)}}}function Tt(t){let e,n,l,o,r,s,c,a=t[15]+"",i=t[16]+"";return{c(){e=p("div"),n=p("span"),l=h(a),o=g(),r=p("span"),s=h(i),c=g(),y(n,"class","stat-name svelte-1c42i8z"),y(r,"class","stat-value svelte-1c42i8z"),y(e,"class","stat-row svelte-1c42i8z")},m(t,a){m(t,e,a),u(e,n),u(n,l),u(e,o),u(e,r),u(r,s),u(e,c)},p(t,e){16&e[0]&&a!==(a=t[15]+"")&&_(l,a),16&e[0]&&i!==(i=t[16]+"")&&_(s,i)},d(t){t&&f(e)}}}function Kt(e){let n;function l(t,e){return t[2]?Et:t[3]?zt:jt}let o=l(e),r=o(e);return{c(){n=p("div"),r.c(),y(n,"class","stats-container svelte-1c42i8z")},m(t,e){m(t,n,e),r.m(n,null)},p(t,e){o===(o=l(t))&&r?r.p(t,e):(r.d(1),r=o(t),r&&(r.c(),r.m(n,null)))},i:t,o:t,d(t){t&&f(n),r.d()}}}function Ot(t,e,n){let{playerName:l}=e,o="general",r=!0,s=null,c=[],a=[],i=[];const u=t=>{n(4,c=Object.entries(t).sort(((t,e)=>e[1]-t[1])).map((([t,e])=>({name:d(t.replace("minecraft:","")),value:p(t.replace("minecraft:",""),e)}))))},m=t=>{const e=["minecraft:broken","minecraft:used","minecraft:dropped","minecraft:crafted","minecraft:mined","minecraft:picked_up"],l={};for(const n of e){const e=t[n]||{};for(const[t,o]of Object.entries(e)){const e=t.replace("minecraft:","").replace(/_/g," ");l[e]||(l[e]={broken:0,used:0,dropped:0,crafted:0,mined:0,picked_up:0}),l[e][n.replace("minecraft:","")]=o}}n(5,a=Object.entries(l).map((([t,e])=>({name:t,...e,total:Object.values(e).reduce(((t,e)=>t+e),0)}))).sort(((t,e)=>e.total-t.total)))},f=t=>{const e=["minecraft:killed","minecraft:killed_by"],l={};for(const n of e){const e=t[n]||{};for(const[t,o]of Object.entries(e)){const e=t.replace("minecraft:","").replace(/_/g," ");l[e]||(l[e]={killed:0,killed_by:0}),l[e][n.replace("minecraft:","")]=o}}n(6,i=Object.entries(l).map((([t,e])=>({name:t,...e,total:Object.values(e).reduce(((t,e)=>t+e),0)}))).sort(((t,e)=>e.total-t.total)))},d=t=>"play_time"===t?"Hours played":"time_since_death"===t?"Minutes since death":"time_since_rest"===t?"Minutes since sleep":"sneak_time"===t?"Hours sneaked":"total_world_time"===t?"Hours logged in":"swim_one_cm"===t?"Kilometers swam":"fly_one_cm"===t?"Kilometers traveled forward and up more than 1 block above the ground":"fall_one_cm"===t?"Kilometers fallen":"sprint_one_cm"===t?"Kilometers sprinted":"walk_on_water_one_cm"===t?"Kilometers bobbed on water":"crouch_one_cm"===t?"Kilometers crouched":"climb_one_cm"===t?"Kilometers climbed":"walk_one_cm"===t?"Kilometers walked":"walk_under_water_one_cm"===t?"Kilometers walked underwater":"boat_one_cm"===t?"Kilometers boated":"minecart_one_cm"===t?"Kilometers carted":"aviate_one_cm"===t?"Kilometers by elytra":"strider_one_cm"===t?"Kilometers Strided":"pig_one_cm"===t?"Kilometers hogged":t.replace(/_/g," "),p=(t,e)=>"play_time"===t?`${(e/72e3).toFixed(1)}`:"time_since_death"===t||"time_since_death"===t?`${(e/1200).toFixed(1)}`:"sneak_time"===t||"total_world_time"===t?`${(e/72e3).toFixed(1)}`:"swim_one_cm"===t||"fly_one_cm"===t||"fall_one_cm"===t||"sprint_one_cm"===t||"walk_on_water_one_cm"===t||"crouch_one_cm"===t||"climb_one_cm"===t||"walk_one_cm"===t||"walk_under_water_one_cm"===t||"boat_one_cm"===t||"minecart_one_cm"===t||"aviate_one_cm"===t||"strider_one_cm"===t||"pig_one_cm"===t?`${(e/1e5).toFixed(2)}`:e;return x((async()=>{try{const t=await fetch(`/api/stats/${l}`);if(!t.ok)throw new Error("Failed to fetch stats");const e=await t.json();u(e["minecraft:custom"]||{}),m(e),f(e)}catch(t){n(3,s=t.message)}finally{n(2,r=!1)}})),t.$$set=t=>{"playerName"in t&&n(0,l=t.playerName)},[l,o,r,s,c,a,i,function(){o=function(t){const e=t.querySelector(":checked");return e&&e.__value}(this),n(1,o)}]}class Ht extends R{constructor(t){super(),G(this,t,Ot,Kt,r,{playerName:0},null,[-1,-1])}}function It(t,e,n){const l=t.slice();return l[1]=e[n].name,l[7]=e[n].value,l}function Ft(t){let e,n,l,o,r,s,c,a=t[1]+"",i=t[0](t[7])+"";return{c(){e=p("li"),n=p("span"),l=h(a),o=g(),r=p("span"),s=h(i),c=g(),y(e,"class","svelte-e7exb0")},m(t,a){m(t,e,a),u(e,n),u(n,l),u(e,o),u(e,r),u(r,s),u(e,c)},p(t,e){4&e&&a!==(a=t[1]+"")&&_(l,a),5&e&&i!==(i=t[0](t[7])+"")&&_(s,i)},d(t){t&&f(e)}}}function Pt(e){let n,l,o,r,s,c=e[2],a=[];for(let t=0;t<c.length;t+=1)a[t]=Ft(It(e,c,t));return{c(){n=p("div"),l=p("h2"),o=h(e[1]),r=g(),s=p("ul");for(let t=0;t<a.length;t+=1)a[t].c();y(l,"class","svelte-e7exb0"),y(s,"class","svelte-e7exb0"),y(n,"class","leaderboard svelte-e7exb0")},m(t,e){m(t,n,e),u(n,l),u(l,o),u(n,r),u(n,s);for(let t=0;t<a.length;t+=1)a[t]&&a[t].m(s,null)},p(t,[e]){if(2&e&&_(o,t[1]),5&e){let n;for(c=t[2],n=0;n<c.length;n+=1){const l=It(t,c,n);a[n]?a[n].p(l,e):(a[n]=Ft(l),a[n].c(),a[n].m(s,null))}for(;n<a.length;n+=1)a[n].d(1);a.length=c.length}},i:t,o:t,d(t){t&&f(n),d(a,t)}}}function Bt(t,e,n){let{name:l}=e,{key:o}=e,{location:r}=e,{formatter:s}=e,{allStats:c=[]}=e,a=[];return t.$$set=t=>{"name"in t&&n(1,l=t.name),"key"in t&&n(3,o=t.key),"location"in t&&n(4,r=t.location),"formatter"in t&&n(0,s=t.formatter),"allStats"in t&&n(5,c=t.allStats)},n(2,a=c.map((t=>{let e=0;return e="computed"===r?t[o]||0:t[r]?.[o]||0,{name:t.name,value:e}})).sort(((t,e)=>e.value-t.value)).slice(0,5)),[s,l,a,o,r,c]}class Dt extends R{constructor(t){super(),G(this,t,Bt,Pt,r,{name:1,key:3,location:4,formatter:0,allStats:5})}}function Jt(t,e,n){const l=t.slice();return l[5]=e[n].name,l[6]=e[n].key,l[7]=e[n].location,l[8]=e[n].formatter,l}function qt(t){let e,n,l,o,r=t[3],s=[];for(let e=0;e<r.length;e+=1)s[e]=Gt(Jt(t,r,e));const c=t=>D(s[t],1,1,(()=>{s[t]=null}));return{c(){e=p("h1"),e.textContent="Leaderboards",n=g(),l=p("div");for(let t=0;t<s.length;t+=1)s[t].c();y(e,"class","svelte-do34ur"),y(l,"class","grid svelte-do34ur")},m(t,r){m(t,e,r),m(t,n,r),m(t,l,r);for(let t=0;t<s.length;t+=1)s[t]&&s[t].m(l,null);o=!0},p(t,e){if(9&e){let n;for(r=t[3],n=0;n<r.length;n+=1){const o=Jt(t,r,n);s[n]?(s[n].p(o,e),B(s[n],1)):(s[n]=Gt(o),s[n].c(),B(s[n],1),s[n].m(l,null))}for(F(),n=r.length;n<s.length;n+=1)c(n);P()}},i(t){if(!o){for(let t=0;t<r.length;t+=1)B(s[t]);o=!0}},o(t){s=s.filter(Boolean);for(let t=0;t<s.length;t+=1)D(s[t]);o=!1},d(t){t&&f(e),t&&f(n),t&&f(l),d(s,t)}}}function At(e){let n,l,o;return{c(){n=p("p"),l=h("Error: "),o=h(e[2])},m(t,e){m(t,n,e),u(n,l),u(n,o)},p(t,e){4&e&&_(o,t[2])},i:t,o:t,d(t){t&&f(n)}}}function Ut(e){let n;return{c(){n=p("p"),n.textContent="Loading leaderboards..."},m(t,e){m(t,n,e)},p:t,i:t,o:t,d(t){t&&f(n)}}}function Gt(t){let e,n;return e=new Dt({props:{name:t[5],key:t[6],location:t[7],formatter:t[8],allStats:t[0]}}),{c(){J(e.$$.fragment)},m(t,l){q(e,t,l),n=!0},p(t,n){const l={};1&n&&(l.allStats=t[0]),e.$set(l)},i(t){n||(B(e.$$.fragment,t),n=!0)},o(t){D(e.$$.fragment,t),n=!1},d(t){A(e,t)}}}function Rt(t){let e,n,l,o;const r=[Ut,At,qt],s=[];function c(t,e){return t[1]?0:t[2]?1:2}return n=c(t),l=s[n]=r[n](t),{c(){e=p("div"),l.c()},m(t,l){m(t,e,l),s[n].m(e,null),o=!0},p(t,[o]){let a=n;n=c(t),n===a?s[n].p(t,o):(F(),D(s[a],1,1,(()=>{s[a]=null})),P(),l=s[n],l?l.p(t,o):(l=s[n]=r[n](t),l.c()),B(l,1),l.m(e,null))},i(t){o||(B(l),o=!0)},o(t){D(l),o=!1},d(t){t&&f(e),s[n].d()}}}function Wt(t,e,n){let l=[],o=[],r=!0,s=null;return x((async()=>{try{const t=await fetch("/api/allStats");if(!t.ok)throw new Error("Failed to fetch stats");const e=await t.json();console.log(e);const r=await fetch("/api/players");if(!r.ok)throw new Error("Failed to fetch players");o=await r.json(),n(0,l=Object.entries(e).map((([t,e])=>{const n=Object.values(e.stats["minecraft:mined"]||{}).reduce(((t,e)=>t+e),0);return{uuid:t,name:o[t],custom:e.stats["minecraft:custom"]||{},total_blocks_mined:n,used:e.stats["minecraft:used"]||{}}}))),console.log(l)}catch(t){n(2,s=t.message),console.error(t.stack)}finally{n(1,r=!1)}})),[l,r,s,[{name:"Playtime",key:"minecraft:play_time",location:"custom",formatter:t=>`${(t/72e3).toFixed(1)} hours`},{name:"Distance Walked",key:"minecraft:walk_one_cm",location:"custom",formatter:t=>`${(t/1e5).toFixed(2)} km`},{name:"Distance by Elytra",key:"minecraft:aviate_one_cm",location:"custom",formatter:t=>`${(t/1e5).toFixed(2)} km`},{name:"Mob Kills",key:"minecraft:mob_kills",location:"custom",formatter:t=>t},{name:"Deaths",key:"minecraft:deaths",location:"custom",formatter:t=>t},{name:"Blocks Mined",key:"total_blocks_mined",location:"computed",formatter:t=>t},{name:"Gravel Placed ;)",key:"minecraft:gravel",location:"used",formatter:t=>t}]]}class Qt extends R{constructor(t){super(),G(this,t,Wt,Rt,r,{})}}const Vt=[];function Xt(e,n=t){let l;const o=new Set;function s(t){if(r(e,t)&&(e=t,l)){const t=!Vt.length;for(const t of o)t[1](),Vt.push(t,e);if(t){for(let t=0;t<Vt.length;t+=2)Vt[t][0](Vt[t+1]);Vt.length=0}}}return{set:s,update:function(t){s(t(e))},subscribe:function(r,c=t){const a=[r,c];return o.add(a),1===o.size&&(l=n(s)||t),r(e),()=>{o.delete(a),0===o.size&&l&&(l(),l=null)}}}}function Yt(t){let e;return{c(){e=p("div"),y(e,"class","tooltip svelte-1cjgc1f"),b(e,"top",t[1]+"px"),b(e,"left",t[2]+"px")},m(n,l){m(n,e,l),e.innerHTML=t[3]},p(t,n){8&n&&(e.innerHTML=t[3]),2&n&&b(e,"top",t[1]+"px"),4&n&&b(e,"left",t[2]+"px")},d(t){t&&f(e)}}}function Zt(e){let n,l=e[0]&&Yt(e);return{c(){l&&l.c(),n=v()},m(t,e){l&&l.m(t,e),m(t,n,e)},p(t,[e]){t[0]?l?l.p(t,e):(l=Yt(t),l.c(),l.m(n.parentNode,n)):l&&(l.d(1),l=null)},i:t,o:t,d(t){l&&l.d(t),t&&f(n)}}}function te(t,e,n){let{show:l=!1}=e,{top:o=0}=e,{left:r=0}=e,{content:s=""}=e;return t.$$set=t=>{"show"in t&&n(0,l=t.show),"top"in t&&n(1,o=t.top),"left"in t&&n(2,r=t.left),"content"in t&&n(3,s=t.content)},[l,o,r,s]}class ee extends R{constructor(t){super(),G(this,t,te,Zt,r,{show:0,top:1,left:2,content:3})}}function ne(t,e,n){const l=t.slice();return l[30]=e[n],l}function le(t,e,n){const l=t.slice();return l[33]=e[n],l}function oe(t,e,n){const l=t.slice();return l[30]=e[n],l}function re(t,e,n){const l=t.slice();return l[33]=e[n],l}function se(t,e,n){const l=t.slice();return l[30]=e[n],l}function ce(t){let e,n,l,o,r,s,c,a,i,v,$,b=t[5],k=[];for(let e=0;e<b.length;e+=1)k[e]=me(se(t,b,e));let w=t[3],x=[];for(let e=0;e<w.length;e+=1)x[e]=ge(oe(t,w,e));let j=t[4],z=[];for(let e=0;e<j.length;e+=1)z[e]=be(ne(t,j,e));return{c(){e=p("h2"),n=h(t[0]),l=h("'s Inventory and Ender Chest"),o=g(),r=p("div");for(let t=0;t<k.length;t+=1)k[t].c();s=g(),c=p("div");for(let t=0;t<x.length;t+=1)x[t].c();a=g(),i=p("div");for(let t=0;t<z.length;t+=1)z[t].c();v=g(),$=p("p"),$.innerHTML="Some items are difficult to render as they don&#39;t have their own textures - e.g. stairs and slabs. Others, I simply don&#39;t have the textures for - e.g. custom mob heads.<br/>If there&#39;s a specific vanilla item that you think is important enough that I should go get the item texture for it, let me know.",y(r,"class","armor-grid svelte-cfa0kj"),y(c,"class","inventory-grid svelte-cfa0kj"),y(i,"class","inventory-grid svelte-cfa0kj")},m(t,f){m(t,e,f),u(e,n),u(e,l),m(t,o,f),m(t,r,f);for(let t=0;t<k.length;t+=1)k[t]&&k[t].m(r,null);m(t,s,f),m(t,c,f);for(let t=0;t<x.length;t+=1)x[t]&&x[t].m(c,null);m(t,a,f),m(t,i,f);for(let t=0;t<z.length;t+=1)z[t]&&z[t].m(i,null);m(t,v,f),m(t,$,f)},p(t,e){if(1&e[0]&&_(n,t[0]),98336&e[0]){let n;for(b=t[5],n=0;n<b.length;n+=1){const l=se(t,b,n);k[n]?k[n].p(l,e):(k[n]=me(l),k[n].c(),k[n].m(r,null))}for(;n<k.length;n+=1)k[n].d(1);k.length=b.length}if(98376&e[0]){let n;for(w=t[3],n=0;n<w.length;n+=1){const l=oe(t,w,n);x[n]?x[n].p(l,e):(x[n]=ge(l),x[n].c(),x[n].m(c,null))}for(;n<x.length;n+=1)x[n].d(1);x.length=w.length}if(98384&e[0]){let n;for(j=t[4],n=0;n<j.length;n+=1){const l=ne(t,j,n);z[n]?z[n].p(l,e):(z[n]=be(l),z[n].c(),z[n].m(i,null))}for(;n<z.length;n+=1)z[n].d(1);z.length=j.length}},d(t){t&&f(e),t&&f(o),t&&f(r),d(k,t),t&&f(s),t&&f(c),d(x,t),t&&f(a),t&&f(i),d(z,t),t&&f(v),t&&f($)}}}function ae(t){let e,n,l;return{c(){e=p("p"),n=h("Error: "),l=h(t[2])},m(t,o){m(t,e,o),u(e,n),u(e,l)},p(t,e){4&e[0]&&_(l,t[2])},d(t){t&&f(e)}}}function ie(t){let e,n,l,o;return{c(){e=p("p"),n=h("Loading stats for "),l=h(t[0]),o=h("...")},m(t,r){m(t,e,r),u(e,n),u(e,l),u(e,o)},p(t,e){1&e[0]&&_(l,t[0])},d(t){t&&f(e)}}}function ue(t){let e,n,l;return{c(){e=p("img"),a(e.src,n=`/api/textures/${t[30].id}`)||y(e,"src",n),y(e,"alt",l=t[30].id),y(e,"class","item-icon")},m(t,n){m(t,e,n)},p(t,o){32&o[0]&&!a(e.src,n=`/api/textures/${t[30].id}`)&&y(e,"src",n),32&o[0]&&l!==(l=t[30].id)&&y(e,"alt",l)},d(t){t&&f(e)}}}function me(t){let e,n,o,r,s="empty"!==t[30].id&&ue(t);function c(...e){return t[17](t[30],...e)}return{c(){e=p("div"),s&&s.c(),n=g(),y(e,"class","item-slot svelte-cfa0kj")},m(l,a){m(l,e,a),s&&s.m(e,null),u(e,n),o||(r=[$(e,"mouseenter",c),$(e,"mouseleave",t[18])],o=!0)},p(l,o){"empty"!==(t=l)[30].id?s?s.p(t,o):(s=ue(t),s.c(),s.m(e,n)):s&&(s.d(1),s=null)},d(t){t&&f(e),s&&s.d(),o=!1,l(r)}}}function fe(t){let e,n,l;return{c(){e=p("img"),a(e.src,n=`/api/textures/${t[30].id}`)||y(e,"src",n),y(e,"alt",l=t[30].id),y(e,"class","item-icon")},m(t,n){m(t,e,n)},p(t,o){8&o[0]&&!a(e.src,n=`/api/textures/${t[30].id}`)&&y(e,"src",n),8&o[0]&&l!==(l=t[30].id)&&y(e,"alt",l)},d(t){t&&f(e)}}}function de(t){let e,n,l=t[6],o=[];for(let e=0;e<l.length;e+=1)o[e]=he(re(t,l,e));return{c(){e=p("div"),n=p("div");for(let t=0;t<o.length;t+=1)o[t].c();y(n,"class","shulker-grid svelte-cfa0kj"),y(e,"class","shulker-tooltip svelte-cfa0kj")},m(t,l){m(t,e,l),u(e,n);for(let t=0;t<o.length;t+=1)o[t]&&o[t].m(n,null)},p(t,e){if(98376&e[0]){let r;for(l=t[6],r=0;r<l.length;r+=1){const s=re(t,l,r);o[r]?o[r].p(s,e):(o[r]=he(s),o[r].c(),o[r].m(n,null))}for(;r<o.length;r+=1)o[r].d(1);o.length=l.length}},d(t){t&&f(e),d(o,t)}}}function pe(t){let e,n,l;return{c(){e=p("img"),a(e.src,n=`/api/textures/${t[33].id}`)||y(e,"src",n),y(e,"alt",l=t[33].id),y(e,"class","item-icon")},m(t,n){m(t,e,n)},p(t,o){64&o[0]&&!a(e.src,n=`/api/textures/${t[33].id}`)&&y(e,"src",n),64&o[0]&&l!==(l=t[33].id)&&y(e,"alt",l)},d(t){t&&f(e)}}}function he(t){let e,n,o,r,s="empty"!==t[30].id&&pe(t);function c(...e){return t[19](t[33],...e)}return{c(){e=p("div"),s&&s.c(),n=g(),y(e,"class","item-slot svelte-cfa0kj")},m(l,a){m(l,e,a),s&&s.m(e,null),u(e,n),o||(r=[$(e,"mouseenter",c),$(e,"mouseleave",t[20])],o=!0)},p(l,o){"empty"!==(t=l)[30].id?s?s.p(t,o):(s=pe(t),s.c(),s.m(e,n)):s&&(s.d(1),s=null)},d(t){t&&f(e),s&&s.d(),o=!1,l(r)}}}function ge(t){let e,n,o,r,s,c,a="empty"!==t[30].id&&fe(t),i=t[30].components&&t[6]===t[30].components.contents&&de(t);function d(...e){return t[21](t[30],...e)}function h(){return t[23](t[30])}return{c(){e=p("div"),a&&a.c(),n=g(),i&&i.c(),o=g(),y(e,"class",r="item-slot "+("empty"===t[30].id?"empty-slot":"")+" svelte-cfa0kj")},m(l,r){m(l,e,r),a&&a.m(e,null),u(e,n),i&&i.m(e,null),u(e,o),s||(c=[$(e,"mouseenter",d),$(e,"mouseleave",t[22]),$(e,"click",h)],s=!0)},p(l,s){"empty"!==(t=l)[30].id?a?a.p(t,s):(a=fe(t),a.c(),a.m(e,n)):a&&(a.d(1),a=null),t[30].components&&t[6]===t[30].components.contents?i?i.p(t,s):(i=de(t),i.c(),i.m(e,o)):i&&(i.d(1),i=null),8&s[0]&&r!==(r="item-slot "+("empty"===t[30].id?"empty-slot":"")+" svelte-cfa0kj")&&y(e,"class",r)},d(t){t&&f(e),a&&a.d(),i&&i.d(),s=!1,l(c)}}}function ve(t){let e,n,l;return{c(){e=p("img"),a(e.src,n=`/api/textures/${t[30].id}`)||y(e,"src",n),y(e,"alt",l=t[30].id),y(e,"class","item-icon")},m(t,n){m(t,e,n)},p(t,o){16&o[0]&&!a(e.src,n=`/api/textures/${t[30].id}`)&&y(e,"src",n),16&o[0]&&l!==(l=t[30].id)&&y(e,"alt",l)},d(t){t&&f(e)}}}function $e(t){let e,n,l=t[6],o=[];for(let e=0;e<l.length;e+=1)o[e]=_e(le(t,l,e));return{c(){e=p("div"),n=p("div");for(let t=0;t<o.length;t+=1)o[t].c();y(n,"class","shulker-grid svelte-cfa0kj"),y(e,"class","shulker-tooltip svelte-cfa0kj")},m(t,l){m(t,e,l),u(e,n);for(let t=0;t<o.length;t+=1)o[t]&&o[t].m(n,null)},p(t,e){if(98384&e[0]){let r;for(l=t[6],r=0;r<l.length;r+=1){const s=le(t,l,r);o[r]?o[r].p(s,e):(o[r]=_e(s),o[r].c(),o[r].m(n,null))}for(;r<o.length;r+=1)o[r].d(1);o.length=l.length}},d(t){t&&f(e),d(o,t)}}}function ye(t){let e,n,l;return{c(){e=p("img"),a(e.src,n=`/api/textures/${t[33].id}`)||y(e,"src",n),y(e,"alt",l=t[33].id),y(e,"class","item-icon")},m(t,n){m(t,e,n)},p(t,o){64&o[0]&&!a(e.src,n=`/api/textures/${t[33].id}`)&&y(e,"src",n),64&o[0]&&l!==(l=t[33].id)&&y(e,"alt",l)},d(t){t&&f(e)}}}function _e(t){let e,n,o,r,s="empty"!==t[30].id&&ye(t);function c(...e){return t[24](t[33],...e)}return{c(){e=p("div"),s&&s.c(),n=g(),y(e,"class","item-slot svelte-cfa0kj")},m(l,a){m(l,e,a),s&&s.m(e,null),u(e,n),o||(r=[$(e,"mouseenter",c),$(e,"mouseleave",t[25])],o=!0)},p(l,o){"empty"!==(t=l)[30].id?s?s.p(t,o):(s=ye(t),s.c(),s.m(e,n)):s&&(s.d(1),s=null)},d(t){t&&f(e),s&&s.d(),o=!1,l(r)}}}function be(t){let e,n,o,r,s,c,a="empty"!==t[30].id&&ve(t),i=t[30].components&&t[6]===t[30].components.contents&&$e(t);function d(...e){return t[26](t[30],...e)}function h(){return t[28](t[30])}return{c(){e=p("div"),a&&a.c(),n=g(),i&&i.c(),o=g(),y(e,"class",r="item-slot "+("empty"===t[30].id?"empty-slot":"")+" svelte-cfa0kj")},m(l,r){m(l,e,r),a&&a.m(e,null),u(e,n),i&&i.m(e,null),u(e,o),s||(c=[$(e,"mouseenter",d),$(e,"mouseleave",t[27]),$(e,"click",h)],s=!0)},p(l,s){"empty"!==(t=l)[30].id?a?a.p(t,s):(a=ve(t),a.c(),a.m(e,n)):a&&(a.d(1),a=null),t[30].components&&t[6]===t[30].components.contents?i?i.p(t,s):(i=$e(t),i.c(),i.m(e,o)):i&&(i.d(1),i=null),16&s[0]&&r!==(r="item-slot "+("empty"===t[30].id?"empty-slot":"")+" svelte-cfa0kj")&&y(e,"class",r)},d(t){t&&f(e),a&&a.d(),i&&i.d(),s=!1,l(c)}}}function ke(t){let e,n,l,o;function r(t,e){return t[1]?ie:t[2]?ae:ce}let s=r(t),c=s(t);return l=new ee({props:{show:t[7],top:t[8],left:t[9],content:t[10]}}),{c(){e=p("div"),c.c(),n=g(),J(l.$$.fragment),y(e,"class","centered svelte-cfa0kj")},m(t,r){m(t,e,r),c.m(e,null),m(t,n,r),q(l,t,r),o=!0},p(t,n){s===(s=r(t))&&c?c.p(t,n):(c.d(1),c=s(t),c&&(c.c(),c.m(e,null)));const o={};128&n[0]&&(o.show=t[7]),256&n[0]&&(o.top=t[8]),512&n[0]&&(o.left=t[9]),1024&n[0]&&(o.content=t[10]),l.$set(o)},i(t){o||(B(l.$$.fragment,t),o=!0)},o(t){D(l.$$.fragment,t),o=!1},d(t){t&&f(e),c.d(),t&&f(n),A(l,t)}}}function we(t,e,n){let l,o,r,s,{playerName:c}=e,a=!0,u=null,m=[],f=[],d=[],p=null,h=Xt(!1);i(t,h,(t=>n(7,l=t)));let g=Xt(0);i(t,g,(t=>n(8,o=t)));let v=Xt(0);i(t,v,(t=>n(9,r=t)));let $=Xt("");function y(t,e){const n=e.target.getBoundingClientRect(),l=function(t){let e=t.components&&t.components.customName?"<b>"+t.components.customName+"</b>\n":"";if(e+=t.id,t.count>1&&(e+=" x"+t.count),t.components&&t.components.Enchantments){e+="\n<b>Enchantments:</b>";for(let n of t.components.Enchantments)e+="\n"+n.name+" "+n.level}return t.components&&t.components.Trim&&(e+="\n<b>Trim:</b>\nPattern: "+t.components.Trim.pattern+"\nMaterial: "+t.components.Trim.material),e}(t),o=document.createElement("div");o.className="tooltip",o.innerText=l,document.body.appendChild(o);const r=o.getBoundingClientRect();document.body.removeChild(o);const s=n.top-r.height-8>=0;h.set(!0),$.set(l),s?g.set(n.top-r.height-8):g.set(n.bottom+8),v.set(n.left+n.width/2)}function _(){h.set(!1)}i(t,$,(t=>n(10,s=t))),x((async()=>{try{const t=await fetch(`/api/inventories/${c}`),e=await t.json(),l=e.Inventory.filter((t=>t.slot<100)).sort(((t,e)=>t.slot-e.slot));n(5,d=e.Inventory.filter((t=>t.slot>=100&&t.slot<=103||-106===t.slot)).sort(((t,e)=>e.slot-t.slot)));const o=e.EnderChest.sort(((t,e)=>t.slot-e.slot));n(3,m=Array(36).fill(null).map(((t,e)=>l.find((t=>t.slot===e))||{id:"empty",count:0,slot:e}))),n(4,f=Array(27).fill(null).map(((t,e)=>o.find((t=>t.slot===e))||{id:"empty",count:0,slot:e})))}catch(t){n(2,u=t.message)}finally{n(1,a=!1)}}));return t.$$set=t=>{"playerName"in t&&n(0,c=t.playerName)},[c,a,u,m,f,d,p,l,o,r,s,h,g,v,$,y,_,(t,e)=>y(t,e),()=>_(),(t,e)=>y(t,e),()=>_(),(t,e)=>y(t,e),()=>_(),t=>{"empty"!==t.id&&t.components&&t.components.contents&&n(6,p=p===t.components.contents?null:t.components.contents)},(t,e)=>y(t,e),()=>_(),(t,e)=>y(t,e),()=>_(),t=>{"empty"!==t.id&&t.components&&t.components.contents&&n(6,p=p===t.components.contents?null:t.components.contents)}]}class xe extends R{constructor(t){super(),G(this,t,we,ke,r,{playerName:0},null,[-1,-1])}}function je(t){let e,n;return e=new xe({props:{playerName:t[1]}}),{c(){J(e.$$.fragment)},m(t,l){q(e,t,l),n=!0},p(t,n){const l={};2&n&&(l.playerName=t[1]),e.$set(l)},i(t){n||(B(e.$$.fragment,t),n=!0)},o(t){D(e.$$.fragment,t),n=!1},d(t){A(e,t)}}}function ze(e){let n,l;return n=new Qt({}),{c(){J(n.$$.fragment)},m(t,e){q(n,t,e),l=!0},p:t,i(t){l||(B(n.$$.fragment,t),l=!0)},o(t){D(n.$$.fragment,t),l=!1},d(t){A(n,t)}}}function Ee(t){let e,n;return e=new Ht({props:{playerName:t[1]}}),{c(){J(e.$$.fragment)},m(t,l){q(e,t,l),n=!0},p(t,n){const l={};2&n&&(l.playerName=t[1]),e.$set(l)},i(t){n||(B(e.$$.fragment,t),n=!0)},o(t){D(e.$$.fragment,t),n=!1},d(t){A(e,t)}}}function Se(e){let n,l;return n=new bt({}),{c(){J(n.$$.fragment)},m(t,e){q(n,t,e),l=!0},p:t,i(t){l||(B(n.$$.fragment,t),l=!0)},o(t){D(n.$$.fragment,t),l=!1},d(t){A(n,t)}}}function Ne(e){let n,l;return n=new yt({}),{c(){J(n.$$.fragment)},m(t,e){q(n,t,e),l=!0},p:t,i(t){l||(B(n.$$.fragment,t),l=!0)},o(t){D(n.$$.fragment,t),l=!1},d(t){A(n,t)}}}function Le(e){let n,l;return n=new mt({}),{c(){J(n.$$.fragment)},m(t,e){q(n,t,e),l=!0},p:t,i(t){l||(B(n.$$.fragment,t),l=!0)},o(t){D(n.$$.fragment,t),l=!1},d(t){A(n,t)}}}function Ce(e){let n,l;return n=new nt({}),{c(){J(n.$$.fragment)},m(t,e){q(n,t,e),l=!0},p:t,i(t){l||(B(n.$$.fragment,t),l=!0)},o(t){D(n.$$.fragment,t),l=!1},d(t){A(n,t)}}}function Me(t){let e,n,l,o,r,s,c,a,i,d;function h(e){t[2](e)}let v={};void 0!==t[0]&&(v.currentRoute=t[0]),n=new Q({props:v}),z.push((()=>function(t,e,n){const l=t.$$.props[e];void 0!==l&&(t.$$.bound[l]=n,n(t.$$.ctx[l]))}(n,"currentRoute",h)));const $=[Ce,Le,Ne,Se,Ee,ze,je],_=[];function b(t,e){return"home"===t[0]?0:"players"===t[0]?1:"backups"===t[0]?2:"map"===t[0]?3:"stats"===t[0]?4:"boards"===t[0]?5:"inventory"===t[0]?6:-1}return~(s=b(t))&&(c=_[s]=$[s](t)),i=new X({}),{c(){e=p("main"),J(n.$$.fragment),o=g(),r=p("div"),c&&c.c(),a=g(),J(i.$$.fragment),y(r,"class","content svelte-1hmc37l"),y(e,"class","svelte-1hmc37l")},m(t,l){m(t,e,l),q(n,e,null),u(e,o),u(e,r),~s&&_[s].m(r,null),u(e,a),q(i,e,null),d=!0},p(t,[e]){const o={};var a;!l&&1&e&&(l=!0,o.currentRoute=t[0],a=()=>l=!1,S.push(a)),n.$set(o);let i=s;s=b(t),s===i?~s&&_[s].p(t,e):(c&&(F(),D(_[i],1,1,(()=>{_[i]=null})),P()),~s?(c=_[s],c?c.p(t,e):(c=_[s]=$[s](t),c.c()),B(c,1),c.m(r,null)):c=null)},i(t){d||(B(n.$$.fragment,t),B(c),B(i.$$.fragment,t),d=!0)},o(t){D(n.$$.fragment,t),D(c),D(i.$$.fragment,t),d=!1},d(t){t&&f(e),A(n),~s&&_[s].d(),A(i)}}}function Te(t,e,n){let l="home",o=null;const r=window.location.pathname;return"/"===r?l="home":"/players"===r?l="players":"/backups"===r?l="backups":"/map"===r?l="map":r.startsWith("/stats/")?(l="stats",o=r.split("/stats/")[1]):"/leaderboards"===r?l="boards":r.startsWith("/inventory/")&&(l="inventory",o=r.split("/inventory/")[1]),[l,o,function(t){l=t,n(0,l)}]}return new class extends R{constructor(t){super(),G(this,t,Te,Me,r,{})}}({target:document.body,props:{name:"world"}})}();
//# sourceMappingURL=bundle.js.map
