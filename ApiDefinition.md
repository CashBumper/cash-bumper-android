**REST**
 * Request money:
	 * POST	/create_requester_session?mac="bla:bla:bla"&amount=9000&account=TBD
	 	=> id
	 * GET	/find_givers_around?lat=43.90&lng=43.09&range=5000
	 	=> list of givers (id, distance, answer=UNSEEN|SEEN|ACCEPTED|REJECTED)
	 * POST /bump?id=43765643&giver_token=8693468

 * Give money:
 	 * POST	/create_giver_session?mac="bla:bla:bla"&max_amount=4333&maximal_range=5000&SEPA
 	 	=> id
 	 * GET	/find_requesters_around?lat=43.90&lng=43.09&range=5000
 	 	=> list of requesters (id, distance, lat, lng)
 	 * POST	/saw_requests?id=4389384&seen_requests=123,456,789
 	 * POST /accept_request?id=4389384&request_id=123123
 	 * POST /decline_request?id=4389384&request_id=123123
 	 * POST /bump?id=43765643&requester_token=8693468
