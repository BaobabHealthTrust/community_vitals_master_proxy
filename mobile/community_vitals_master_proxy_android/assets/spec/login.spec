P.1. Login [program => COMMUNITY VITALS REGISTRATION $$ action => javascript:doLogin()]
Q.1.1. Username [pos => 0 $$ tt_onLoad => current_case_upper = false $$ tt_showToggleKeyboard => false]
Q.1.2. Password [pos => 1 $$ field_type => password $$ tt_onLoad => current_case_upper = false $$ tt_showToggleKeyboard => false]
Q.1.3. User Type [pos => 2 $$ tt_showToggleKeyboard => true]
O.1.3.1. Traditional Authority {0}
O.1.3.2. Group Village Headman {1}
O.1.3.3. Village Headman {2}
Q.1.4. T/A Location [pos => 3 $$ dynamicLoader => listTAs(__$('inputField').value.trim()) $$ condition => __$('1.3').value == 0]
O.1.4.1. T/A Locations List
Q.1.5. GVH Location [pos => 4 $$ dynamicLoader => listGVHs(__$('inputField').value.trim()) $$ condition => __$('1.3').value == 1]
O.1.5.1. Locations List
Q.1.6. Village Location [pos => 5 $$ dynamicLoader => listVH(__$('inputField').value.trim()) $$ condition => __$('1.3').value == 2]
O.1.6.1. Locations List
