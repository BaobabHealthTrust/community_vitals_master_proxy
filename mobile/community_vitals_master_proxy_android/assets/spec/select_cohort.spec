P.1. Select Cohort [program: COMMUNITY VITALS REGISTRATION $$ action => javascript:cohort_report()()]
C.1. Community Vitals Registration get parameters for cohort report
C.1. Special characters are:
C.1. "=>" for association
C.1. "$$" for separation of entries

Q.1.1. Report Duration [pos => 0 $$ tt_showToggleKeyboard => false $$ showKeys => false $$ dynamicLoader => loadCohortType(__$('inputField').value.trim()) $$ tt_onLoad => loadCohortType('')]
Q.1.2. Select Quarter [pos => 1 $$ tt_showToggleKeyboard => true $$ showKeys => false $$ tt_onLoad => load_quarters('') $$ dynamicLoader => load_quarters(__$('inputField').value.trim()) $$ condition => __$('1.1').value == 'Quarter']
Q.1.3. Select Year [pos => 2 $$ tt_showToggleKeyboard => true $$ showKeys => false $$ tt_onLoad => load_years('') $$ dynamicLoader => load_years(__$('inputField').value.trim()) $$ condition => __$('1.1').value == 'Annual']