P.1. Login [program => COMMUNITY VITALS REGISTRATION $$ action => javascript:doLogin()]
Q.1.1. Username [pos => 0 $$ tt_onLoad => current_case_upper = false $$ tt_showToggleKeyboard => false]

Q.1.2. Password [pos => 1 $$ field_type => password $$ tt_onLoad => current_case_upper = false $$ tt_showToggleKeyboard => false]

Q.1.3. User Type [pos => 2 $$ tt_showToggleKeyboard => true $$ dynamicLoader => loadChiefs(__$('inputField').value.trim()) $$ tt_onLoad => loadChiefs('')]

Q.1.4. T/A Location [pos => 3 $$ tt_showToggleKeyboard => true $$ tt_onLoad => listTAs('') $$ dynamicLoader => listTAs(__$('inputField').value.trim()) $$ condition => __$('1.3').value == 0]

Q.1.5. GVH Location [pos => 4 $$ tt_showToggleKeyboard => true $$ tt_onLoad => listGVHs('') $$ dynamicLoader => listGVHs(__$('inputField').value.trim()) $$ condition => __$('1.3').value == 1]

Q.1.6. Village Location [pos => 5 $$ tt_showToggleKeyboard => true $$ tt_onLoad => listVH('') $$ dynamicLoader => listVH(__$('inputField').value.trim()) $$ condition => __$('1.3').value == 2]
