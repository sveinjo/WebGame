// DynLayerTest Function
// test the layer target before creating the DynLayer, just include this file after the DynLayer.js if you want to use the test (not manditory)
// 19990624

// Copyright (C) 1999 Dan Steinman
// Distributed under the terms of the GNU Library General Public License
// Available at http://www.dansteinman.com/dynapi/

function DynLayerTest(id,nestref) {
	var ref = new Array()
	if (nestref) ref = nestref.split('.document.')
	ref[ref.length] = id
	var refstr = (is.ns)? 'document.'+ref[0] : 'document.all.'+ref[0]
	for (var i=1; i<=ref.length; i++) {
		if (eval(refstr)) {
			if (ref.length==i) return true
			else refstr += (is.ns)? '.document.'+ref[i] : '.all.'+ref[i]
		}
		else {
			var str ='DynLayer Error:'
			for (j in ref) {
				str += '\n'+ref[j]
				if (j==i-1) str += '  <-- this layer cannot be found'
			}
			//if (DynLayerTest.count++<5) alert(str)
			//else alert("Too many DynLayer errors, quitting.")
			return false
		}
	}
	return false
}
DynLayerTest.count = 0
