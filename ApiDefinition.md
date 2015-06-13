**REST**
 * Request money:
	 * POST	/create_session?mac="bla:bla:bla"&isOffering="true|false"
	 		returns id or atmid
	 * POST	/request_money?amount=9000&account=TBD&id=4387674
	 * GET	/find_money?lat=43.90&lng=43.09&range=5000&id=3489438 (pull)
	 * POST	/choose_atm?id=34567949?atmid=374673274
	 * POST /cancel?id=783267647
	 * POST /bump?id=43765643&atmid=8693468
 * Lend money:
 	 * Create session as well
 	 * POST	/give_money?information=""&atmid=9834302&amount=4333&maximalrange=5000
 	 * GET	/pull?lat=39.43&lng=43.54&atmid=23647834 (in Background on the apps)
 	 * POST	/request_seen?array_seen=[....]&atmid=4389384
 	 * POST /accept?atmid=44398&id=893843
 	 * POST /decline?atmid=44398&id=893843
