/*! SmartAdmin - v1.5 - 2014-09-27 */
!
function(a, b, c) {
    var d = function(a) {
        "use strict";
        var d = function(b, c) {
            return this.CLASS && "ColVis" == this.CLASS || alert("Warning: ColVis must be initialised with the keyword 'new'"),
            "undefined" == typeof c && (c = {}),
            a.fn.dataTable.camelToHungarian && a.fn.dataTable.camelToHungarian(d.defaults, c),
            this.s = {
                "dt": null,
                "oInit": c,
                "hidden": !0,
                "abOriginal": []
            },
            this.dom = {
                "wrapper": null,
                "button": null,
                "collection": null,
                "background": null,
                "catcher": null,
                "buttons": [],
                "groupButtons": [],
                "restore": null
            },
            d.aInstances.push(this),
            this.s.dt = a.fn.dataTable.Api ? new a.fn.dataTable.Api(b).settings()[0] : b,
            this._fnConstruct(c),
            this
        };
        return d.prototype = {
            "button": function() {
                return this.dom.wrapper
            },
            "fnRebuild": function() {
                this.rebuild()
            },
            "rebuild": function() {
                for (var a = this.dom.buttons.length - 1; a >= 0; a--) this.dom.collection.removeChild(this.dom.buttons[a]);
                this.dom.buttons.splice(0, this.dom.buttons.length),
                this.dom.restore && this.dom.restore.parentNode(this.dom.restore),
                this._fnAddGroups(),
                this._fnAddButtons(),
                this._fnDrawCallback()
            },
            "_fnConstruct": function(c) {
                this._fnApplyCustomisation(c);
                var d, e, f = this;
                for (this.dom.wrapper = b.createElement("div"), this.dom.wrapper.className = "ColVis", this.dom.button = a("<button />", {
                    "class": this.s.dt.bJUI ? "ColVis_Button ColVis_MasterButton ui-button ui-state-default": "ColVis_Button ColVis_MasterButton"
                }).append("<span>" + this.s.buttonText + "</span>").bind("mouseover" == this.s.activate ? "mouseover": "click",
                function(a) {
                    a.preventDefault(),
                    f._fnCollectionShow()
                }).appendTo(this.dom.wrapper)[0], this.dom.catcher = this._fnDomCatcher(), this.dom.collection = this._fnDomCollection(), this.dom.background = this._fnDomBackground(), this._fnAddGroups(), this._fnAddButtons(), d = 0, e = this.s.dt.aoColumns.length; e > d; d++) this.s.abOriginal.push(this.s.dt.aoColumns[d].bVisible);
                this.s.dt.aoDrawCallback.push({
                    "fn": function() {
                        f._fnDrawCallback.call(f)
                    },
                    "sName": "ColVis"
                }),
                a(this.s.dt.oInstance).bind("column-reorder",
                function(a, b, c) {
                    for (d = 0, e = f.s.aiExclude.length; e > d; d++) f.s.aiExclude[d] = c.aiInvertMapping[f.s.aiExclude[d]];
                    var g = f.s.abOriginal.splice(c.iFrom, 1)[0];
                    f.s.abOriginal.splice(c.iTo, 0, g),
                    f.fnRebuild()
                }),
                this._fnDrawCallback()
            },
            "_fnApplyCustomisation": function(b) {
                a.extend(!0, this.s, d.defaults, b),
                !this.s.showAll && this.s.bShowAll && (this.s.showAll = this.s.sShowAll),
                !this.s.restore && this.s.bRestore && (this.s.restore = this.s.sRestore);
                var c = this.s.groups,
                e = this.s.aoGroups;
                if (c) for (var f = 0,
                g = c.length; g > f; f++) c[f].title && (e[f].sTitle = c[f].title),
                c[f].columns && (e[f].aiColumns = c[f].columns)
            },
            "_fnDrawCallback": function() {
                for (var b, d = this.s.dt.aoColumns,
                e = this.dom.buttons,
                f = this.s.aoGroups,
                g = 0,
                h = e.length; h > g; g++) b = e[g],
                b.__columnIdx !== c && a("input", b).prop("checked", d[b.__columnIdx].bVisible);
                for (var i = function(a) {
                    for (var b = 0,
                    c = a.length; c > b; b++) if (d[a[b]].bVisible === !1) return ! 1;
                    return ! 0
                },
                j = function(a) {
                    for (var b = 0,
                    c = a.length; c > b; b++) if (d[a[b]].bVisible === !0) return ! 1;
                    return ! 0
                },
                k = 0, l = f.length; l > k; k++) i(f[k].aiColumns) ? (a("input", this.dom.groupButtons[k]).prop("checked", !0), a("input", this.dom.groupButtons[k]).prop("indeterminate", !1)) : j(f[k].aiColumns) ? (a("input", this.dom.groupButtons[k]).prop("checked", !1), a("input", this.dom.groupButtons[k]).prop("indeterminate", !1)) : a("input", this.dom.groupButtons[k]).prop("indeterminate", !0)
            },
            "_fnAddGroups": function() {
                var a;
                if ("undefined" != typeof this.s.aoGroups) for (var b = 0,
                c = this.s.aoGroups.length; c > b; b++) a = this._fnDomGroupButton(b),
                this.dom.groupButtons.push(a),
                this.dom.buttons.push(a),
                this.dom.collection.appendChild(a)
            },
            "_fnAddButtons": function() {
                var b, c = this.s.dt.aoColumns;
                if ( - 1 === a.inArray("all", this.s.aiExclude)) for (var d = 0,
                e = c.length; e > d; d++) - 1 === a.inArray(d, this.s.aiExclude) && (b = this._fnDomColumnButton(d), b.__columnIdx = d, this.dom.buttons.push(b));
                "alpha" === this.s.order && this.dom.buttons.sort(function(a, b) {
                    var d = c[a.__columnIdx].sTitle,
                    e = c[b.__columnIdx].sTitle;
                    return d === e ? 0 : e > d ? -1 : 1
                }),
                this.s.restore && (b = this._fnDomRestoreButton(), b.className += " ColVis_Restore", this.dom.buttons.push(b)),
                this.s.showAll && (b = this._fnDomShowAllButton(), b.className += " ColVis_ShowAll", this.dom.buttons.push(b)),
                a(this.dom.collection).append(this.dom.buttons)
            },
            "_fnDomRestoreButton": function() {
                var b = this,
                c = this.s.dt;
                return a('<li class="ColVis_Special ' + (c.bJUI ? "ui-button ui-state-default": "") + '">' + this.s.restore + "</li>").click(function() {
                    for (var a = 0,
                    c = b.s.abOriginal.length; c > a; a++) b.s.dt.oInstance.fnSetColumnVis(a, b.s.abOriginal[a], !1);
                    b._fnAdjustOpenRows(),
                    b.s.dt.oInstance.fnAdjustColumnSizing(!1),
                    b.s.dt.oInstance.fnDraw(!1)
                })[0]
            },
            "_fnDomShowAllButton": function() {
                var b = this,
                c = this.s.dt;
                return a('<li class="ColVis_Special ' + (c.bJUI ? "ui-button ui-state-default": "") + '">' + this.s.showAll + "</li>").click(function() {
                    for (var a = 0,
                    c = b.s.abOriginal.length; c > a; a++) - 1 === b.s.aiExclude.indexOf(a) && b.s.dt.oInstance.fnSetColumnVis(a, !0, !1);
                    b._fnAdjustOpenRows(),
                    b.s.dt.oInstance.fnAdjustColumnSizing(!1),
                    b.s.dt.oInstance.fnDraw(!1)
                })[0]
            },
            "_fnDomGroupButton": function(b) {
                var c = this,
                d = this.s.dt,
                e = this.s.aoGroups[b];
                return a('<li class="ColVis_Special ' + (d.bJUI ? "ui-button ui-state-default": "") + '"><label><input type="checkbox" /><span>' + e.sTitle + "</span></label></li>").click(function(b) {
                    var d = !a("input", this).is(":checked");
                    "li" !== b.target.nodeName.toLowerCase() && (d = !d);
                    for (var f = 0; f < e.aiColumns.length; f++) c.s.dt.oInstance.fnSetColumnVis(e.aiColumns[f], d)
                })[0]
            },
            "_fnDomColumnButton": function(b) {
                var c = this,
                d = this.s.dt.aoColumns[b],
                e = this.s.dt,
                f = null === this.s.fnLabel ? d.sTitle: this.s.fnLabel(b, d.sTitle, d.nTh);
                return a("<li " + (e.bJUI ? 'class="ui-button ui-state-default"': "") + '><label><input type="checkbox" /><span>' + f + "</span></label></li>").click(function(d) {
                    var f = !a("input", this).is(":checked");
                    "li" !== d.target.nodeName.toLowerCase() && (f = !f);
                    var g = a.fn.dataTableExt.iApiIndex;
                    a.fn.dataTableExt.iApiIndex = c._fnDataTablesApiIndex.call(c),
                    e.oFeatures.bServerSide ? (c.s.dt.oInstance.fnSetColumnVis(b, f, !1), c.s.dt.oInstance.fnAdjustColumnSizing(!1), ("" !== e.oScroll.sX || "" !== e.oScroll.sY) && c.s.dt.oInstance.oApi._fnScrollDraw(c.s.dt), c._fnDrawCallback()) : c.s.dt.oInstance.fnSetColumnVis(b, f),
                    a.fn.dataTableExt.iApiIndex = g,
                    null !== c.s.fnStateChange && c.s.fnStateChange.call(c, b, f)
                })[0]
            },
            "_fnDataTablesApiIndex": function() {
                for (var a = 0,
                b = this.s.dt.oInstance.length; b > a; a++) if (this.s.dt.oInstance[a] == this.s.dt.nTable) return a;
                return 0
            },
            "_fnDomCollection": function() {
                return a("<ul />", {
                    "class": this.s.dt.bJUI ? "ColVis_collection ui-buttonset ui-buttonset-multi": "ColVis_collection"
                }).css({
                    "display": "none",
                    "opacity": 0,
                    "position": this.s.bCssPosition ? "": "absolute"
                })[0]
            },
            "_fnDomCatcher": function() {
                var c = this,
                d = b.createElement("div");
                return d.className = "ColVis_catcher",
                a(d).click(function() {
                    c._fnCollectionHide.call(c, null, null)
                }),
                d
            },
            "_fnDomBackground": function() {
                var b = this,
                c = a("<div></div>").addClass("ColVis_collectionBackground").css("opacity", 0).click(function() {
                    b._fnCollectionHide.call(b, null, null)
                });
                return "mouseover" == this.s.activate && c.mouseover(function() {
                    b.s.overcollection = !1,
                    b._fnCollectionHide.call(b, null, null)
                }),
                c[0]
            },
            "_fnCollectionShow": function() {
                var c, d = this,
                e = a(this.dom.button).offset(),
                f = this.dom.collection,
                g = this.dom.background,
                h = parseInt(e.left, 10),
                i = parseInt(e.top + a(this.dom.button).outerHeight(), 10);
                this.s.bCssPosition || (f.style.top = i + "px", f.style.left = h + "px"),
                a(f).css({
                    "display": "block",
                    "opacity": 0
                }),
                g.style.bottom = "0px",
                g.style.right = "0px";
                var j = this.dom.catcher.style;
                if (j.height = a(this.dom.button).outerHeight() + "px", j.width = a(this.dom.button).outerWidth() + "px", j.top = e.top + "px", j.left = h + "px", b.body.appendChild(g), b.body.appendChild(f), b.body.appendChild(this.dom.catcher), a(f).animate({
                    "opacity": 1
                },
                d.s.iOverlayFade), a(g).animate({
                    "opacity": .1
                },
                d.s.iOverlayFade, "linear",
                function() {
                    a.browser && a.browser.msie && "6.0" == a.browser.version && d._fnDrawCallback()
                }), !this.s.bCssPosition) {
                    c = "left" == this.s.sAlign ? h: h - a(f).outerWidth() + a(this.dom.button).outerWidth(),
                    f.style.left = c + "px";
                    var k = a(f).outerWidth(),
                    l = (a(f).outerHeight(), a(b).width());
                    c + k > l && (f.style.left = l - k + "px")
                }
                this.s.hidden = !1
            },
            "_fnCollectionHide": function() {
                var c = this;
                this.s.hidden || null === this.dom.collection || (this.s.hidden = !0, a(this.dom.collection).animate({
                    "opacity": 0
                },
                c.s.iOverlayFade,
                function() {
                    this.style.display = "none"
                }), a(this.dom.background).animate({
                    "opacity": 0
                },
                c.s.iOverlayFade,
                function() {
                    b.body.removeChild(c.dom.background),
                    b.body.removeChild(c.dom.catcher)
                }))
            },
            "_fnAdjustOpenRows": function() {
                for (var a = this.s.dt.aoOpenRows,
                b = this.s.dt.oApi._fnVisbleColumns(this.s.dt), c = 0, d = a.length; d > c; c++) a[c].nTr.getElementsByTagName("td")[0].colSpan = b
            }
        },
        d.fnRebuild = function(a) {
            var b = null;
            "undefined" != typeof a && (b = a.fnSettings().nTable);
            for (var c = 0,
            e = d.aInstances.length; e > c; c++)("undefined" == typeof a || b == d.aInstances[c].s.dt.nTable) && d.aInstances[c].fnRebuild()
        },
        d.defaults = {
            "active": "click",
            "buttonText": " 显示/ 隐藏 一列或多列",
            "aiExclude": [],
            "bRestore": !1,
            "sRestore": "Restore original",
            "bShowAll": !1,
            "sShowAll": "Show All",
            "sAlign": "left",
            "fnStateChange": null,
            "iOverlayFade": 500,
            "fnLabel": null,
            "bCssPosition": !1,
            "aoGroups": [],
            "order": "column"
        },
        d.aInstances = [],
        d.prototype.CLASS = "ColVis",
        d.VERSION = "1.1.0",
        d.prototype.VERSION = d.VERSION,
        "function" == typeof a.fn.dataTable && "function" == typeof a.fn.dataTableExt.fnVersionCheck && a.fn.dataTableExt.fnVersionCheck("1.7.0") ? a.fn.dataTableExt.aoFeatures.push({
            "fnInit": function(a) {
                var b = a.oInit,
                c = new d(a, b.colVis || b.oColVis || {});
                return c.button()
            },
            "cFeature": "C",
            "sFeature": "ColVis"
        }) : alert("Warning: ColVis requires DataTables 1.7 or greater - www.datatables.net/download"),
        a.fn.dataTable.ColVis = d,
        a.fn.DataTable.ColVis = d,
        d
    };
    "function" == typeof define && define.amd ? define("datatables-colvis", ["jquery", "datatables"], d) : jQuery && !jQuery.fn.dataTable.ColVis && d(jQuery, jQuery.fn.dataTable)
} (window, document);