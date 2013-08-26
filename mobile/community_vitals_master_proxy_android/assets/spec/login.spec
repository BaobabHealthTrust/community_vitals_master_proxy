P.1. Login [program => COMMUNITY VITALS REGISTRATION $$ action => javascript:doLogin()]
Q.1.1. Change Language [pos => 0]
O.1.1.1. English {en}
O.1.1.2. Chichewa {ny}
O.1.1.3. Tumbuka {tu}
O.1.1.4. Yao {yo}
Q.1.2. Username [pos => 1 $$ tt_onLoad => current_case_upper = false]
Q.1.3. Password [pos => 2 $$ field_type => password $$ tt_onLoad => current_case_upper = false]
Q.1.4. User Type [pos => 3]
O.1.4.1. Traditional Authority {0}
O.1.4.2. Group Village Headman {1}
O.1.4.3. Village Headman {2}
Q.1.5. T/A Location [pos => 4 $$ dynamicLoader => listTAs(__$('inputField').value.trim()) $$ condition => __$('1.4').value == 0]
O.1.5.1. T/A Locations List
Q.1.6. GVH Location [pos => 5 $$ dynamicLoader => listGVHs(__$('inputField').value.trim()) $$ condition => __$('1.4').value == 1]
O.1.6.1. Locations List
Q.1.7. Village Location [pos => 5 $$ dynamicLoader => listVH(__$('inputField').value.trim()) $$ condition => __$('1.4').value == 2]
O.1.7.1. Locations List
