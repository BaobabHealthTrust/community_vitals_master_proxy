P.1. Category [program => COMMUNITY VITALS REGISTRATION $$ action => javascript:setCategory()]

Q.1.1. User Type [pos => 0 $$ tt_showToggleKeyboard => false $$ showKeys => false $$ dynamicLoader => listCurrentUserRoles(__$('inputField').value.trim()) $$ tt_onLoad => listCurrentUserRoles('')]

Q.1.2. Secondary User Type [pos => 1 $$ tt_showToggleKeyboard => false $$ showKeys => false $$ dynamicLoader => listCurrentUserRoles(__$('inputField').value.trim(), 'Superuser') $$ tt_onLoad => listCurrentUserRoles('', 'Superuser') $$ condition => __$("1.1").value.toLowerCase() == "superuser"]

C.1.3. Select Location Traditional Authority [pos => 2 $$ tt_showToggleKeyboard => true $$ tt_onLoad => listTAs('') $$ dynamicLoader => listTAs(__$('inputField').value.trim())]

C.1.4. Select Location Group Village Headman [pos => 3 $$ tt_showToggleKeyboard => true $$ tt_onLoad => listGVHs('') $$ dynamicLoader => listGVHs(__$('inputField').value.trim()) $$ condition => (__$('1.1').value.toLowerCase() == "superuser" && (__$('1.2').value.toLowerCase() == 'group village headman' || __$('1.2').value.toLowerCase() == 'village headman')) || (__$('1.1').value.toLowerCase() == 'group village headman' || __$('1.1').value.toLowerCase() == 'village headman')]

C.1.5. Select Location Village Headman [pos => 4 $$ tt_showToggleKeyboard => true $$ tt_onLoad => listVH('') $$ dynamicLoader => listVH(__$('inputField').value.trim()) $$ condition => (__$('1.1').value.toLowerCase() == "superuser" && __$('1.2').value.toLowerCase() == 'village headman') || __$('1.1').value.toLowerCase() == 'village headman']
