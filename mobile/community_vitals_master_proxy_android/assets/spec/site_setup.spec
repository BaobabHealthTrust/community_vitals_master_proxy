P.1. Site Setup [program => COMMUNITY VITALS REGISTRATION $$ action => javascript:setupSite()]
C.1. Community Vitals Registration person registration
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. Target Site Username [pos => a $$ tt_onLoad => current_case_upper = false; var user = Android.getPref("target_username"); __$("inputField").value = user; $$ tt_showToggleKeyboard => false $$ condition => (window.location.href.match(/\?/) ? (window.location.href.match(/username/i) ? true : false) : true)]

Q.1.2. Target Site Password [pos => b $$ field_type => password $$ tt_onLoad => current_case_upper = false $$ tt_showToggleKeyboard => false $$ condition => (window.location.href.match(/\?/) ? (window.location.href.match(/password/i) ? true : false) : true)]

Q.1.3. Target Server IP Address [pos => c $$ field_type => number $$ validationRule => ^\d+\.\d+\.\d+\.\d+$ $$ validationMessage => Wrong value. Expecting an IP address of format xxx.xxx.xxx.xxx! $$ condition => (window.location.href.match(/\?/) ? (window.location.href.match(/ip/i) ? true : false) : true) $$ tt_onLoad => var ip = Android.getPref("target_server"); __$("inputField").value = ip;]

Q.1.4. Target Server IP Address Port [pos => d $$ field_type => number $$ validationRule => ^\d+$ $$ validationMessage => Wrong value. Expecting a number! $$ condition => (window.location.href.match(/\?/) ? (window.location.href.match(/ip/i) ? true : false) : true) $$ tt_onLoad => var port = Android.getPref("target_port"); __$("inputField").value = port;]

Q.1.5. Site Code [pos => e $$ condition => (window.location.href.match(/\?/) ? (window.location.href.match(/code/i) ? true : false) : true) $$ tt_onLoad => var code = Android.getPref("site_code"); __$("inputField").value = code;]

Q.1.6. ID Batch Size [pos => f $$ field_type => number $$ validationRule => ^\d+$ $$ validationMessage => Wrong value. Expecting whole numbers! $$ condition => (window.location.href.match(/\?/) ? (window.location.href.match(/batch/i) ? true : false) : true) $$ tt_onLoad => var count = Android.getPref("batch_count"); __$("inputField").value = count;]

Q.1.7. ID Lower Threshold Size [pos => g $$ field_type => number $$ validationRule => ^\d+$ $$ validationMessage => Wrong value. Expecting whole numbers! $$ condition => (window.location.href.match(/\?/) ? (window.location.href.match(/threshold/i) ? true : false) : true) $$ tt_onLoad => var threshold = Android.getThreshold(); __$("inputField").value = threshold;]

Q.1.8. User Type [pos => h $$ tt_showToggleKeyboard => false $$ showKeys => false $$ dynamicLoader => loadChiefs(__$('inputField').value.trim()) $$ tt_onLoad => loadChiefs('')]

Q.1.9. Select Region [pos => i $$ dynamicLoader => loadRegion(__$('inputField').value.trim()) $$ tt_onLoad => loadRegion('') $$ tt_showToggleKeyboard => true $$ showKeys => false]

Q.1.10. Select District [pos => j $$ dynamicLoader => loadDistrict(__$('inputField').value.trim(),__$("1.9").value.trim().toLowerCase()) $$ tt_onLoad => loadDistrict('',__$("1.9").value.trim().toLowerCase()) $$ tt_showToggleKeyboard => false $$ showKeys => false]

Q.1.11. Select Traditional Authority [pos => k $$ dynamicLoader => loadTA(__$('inputField').value.trim(),__$("1.10").value.trim().toLowerCase()) $$ tt_onLoad => loadTA('',__$("1.10").value.trim().toLowerCase()) $$ tt_showToggleKeyboard => true $$ showKeys => true]

Q.1.12. Select Location Group Village Headman [pos => m $$ dynamicLoader => loadVillage(__$('inputField').value.trim(),__$("1.11").value.trim().toLowerCase()) $$ tt_onLoad => loadVillage('',__$("1.11").value.trim().toLowerCase()) $$ tt_showToggleKeyboard => true $$ showKeys => false $$ condition => (__$('1.8').value == 'gvh' || __$('1.8').value == 'vh')]

Q.1.13. Select Location Village Headman [pos => m $$ dynamicLoader => loadVillage(__$('inputField').value.trim(),__$("1.11").value.trim().toLowerCase()) $$ tt_onLoad => loadVillage('',__$("1.11").value.trim().toLowerCase()) $$ tt_showToggleKeyboard => true $$ showKeys => false $$ condition => (__$('1.8').value == 'gvh' || __$('1.8').value == 'vh')]


