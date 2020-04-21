(this["webpackJsonptask-manager-front"] = this["webpackJsonptask-manager-front"] || []).push([[0], {
    346: function (e, t, n) {
        e.exports = n(443)
    }, 351: function (e, t, n) {
    }, 352: function (e, t, n) {
        e.exports = n.p + "static/media/logo.5d5d9eef.svg"
    }, 353: function (e, t, n) {
    }, 443: function (e, t, n) {
        "use strict";
        n.r(t);
        var a = n(0), r = n.n(a), c = n(16), f = n.n(c), i = (n(351), n(336)), o = n(313), s = n(314), u = n(337),
            l = n(315), d = n(338), b = (n(352), n(353), n(316)), j = n.n(b), O = n(323), m = n.n(O), g = n(333),
            E = n.n(g), h = n(324), w = n.n(h), p = n(331), R = n.n(p), v = n(215), k = n.n(v), y = n(214), P = n.n(y),
            x = n(325), C = n.n(x), D = n(326), S = n.n(D), A = n(328), F = n.n(A), I = n(329), J = n.n(I), B = n(330),
            L = n.n(B), M = n(334), N = n.n(M), T = n(327), V = n.n(T), W = n(332), q = n.n(W), z = n(335), G = n.n(z),
            H = {
                Add: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(m.a, Object.assign({}, e, {ref: t}))
                })), Check: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(w.a, Object.assign({}, e, {ref: t}))
                })), Clear: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(P.a, Object.assign({}, e, {ref: t}))
                })), Delete: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(C.a, Object.assign({}, e, {ref: t}))
                })), DetailPanel: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(k.a, Object.assign({}, e, {ref: t}))
                })), Edit: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(S.a, Object.assign({}, e, {ref: t}))
                })), Export: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(V.a, Object.assign({}, e, {ref: t}))
                })), Filter: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(F.a, Object.assign({}, e, {ref: t}))
                })), FirstPage: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(J.a, Object.assign({}, e, {ref: t}))
                })), LastPage: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(L.a, Object.assign({}, e, {ref: t}))
                })), NextPage: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(k.a, Object.assign({}, e, {ref: t}))
                })), PreviousPage: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(R.a, Object.assign({}, e, {ref: t}))
                })), ResetSearch: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(P.a, Object.assign({}, e, {ref: t}))
                })), Search: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(q.a, Object.assign({}, e, {ref: t}))
                })), SortArrow: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(E.a, Object.assign({}, e, {ref: t}))
                })), ThirdStateCheck: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(N.a, Object.assign({}, e, {ref: t}))
                })), ViewColumn: Object(a.forwardRef)((function (e, t) {
                    return r.a.createElement(G.a, Object.assign({}, e, {ref: t}))
                }))
            }, K = function (e) {
                function t() {
                    var e, n;
                    Object(o.a)(this, t);
                    for (var a = arguments.length, r = new Array(a), c = 0; c < a; c++) r[c] = arguments[c];
                    return (n = Object(u.a)(this, (e = Object(l.a)(t)).call.apply(e, [this].concat(r)))).state = {data: []}, n
                }

                return Object(d.a)(t, e), Object(s.a)(t, [{
                    key: "componentDidMount", value: function () {
                        var e = this;
                        fetch("http://localhost:8080/rest/api/tasks").then((function (e) {
                            return e.json()
                        })).then((function (t) {
                            console.log(t);
                            for (var n = [], a = 0, r = Object.entries(t); a < r.length; a++) {
                                var c = Object(i.a)(r[a], 2), f = (c[0], c[1]);
                                n.push(f)
                            }
                            e.setState({data: n})
                        }))
                    }
                }, {
                    key: "render", value: function () {
                        return r.a.createElement("div", {style: {maxWidth: "100%"}}, r.a.createElement(j.a, {
                            columns: [{
                                title: "\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435",
                                field: "name"
                            }, {
                                title: "\u041e\u043f\u0438\u0441\u0430\u043d\u0438\u0435",
                                field: "description"
                            }, {
                                title: "\u0414\u0430\u0442\u0430 \u0441\u043e\u0437\u0434\u0430\u043d\u0438\u044f",
                                field: "creationDate",
                                type: "date"
                            }, {
                                title: "\u0414\u0430\u0442\u0430 \u0432\u044b\u043f\u043e\u043b\u043d\u0435\u043d\u0438\u044f",
                                field: "dueDate",
                                type: "date"
                            }, {title: "\u0421\u0442\u0430\u0442\u0443\u0441", field: "statusId"}],
                            data: this.state.data,
                            title: "\u0414\u0438\u0441\u043f\u0435\u0442\u0447\u0435\u0440 \u0437\u0430\u0434\u0430\u0447",
                            icons: H
                        }))
                    }
                }]), t
            }(r.a.Component);
        f.a.render(r.a.createElement(K, null), document.getElementById("root"))
    }
}, [[346, 1, 2]]]);
//# sourceMappingURL=main.da993e5c.chunk.js.map