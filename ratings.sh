# generate some random ratings for the dummy articles
for user in {dombusser,manuelgerstner,christinenoehmeier,davidfrey,dummyuser1,dummyuser2,dummyuser3,dummyuser4,dummyuser5,dummyuser6,dummyuser7,dummyuser8,dummyuser9,dummyuser10,dummyuser11,dummyuser12,dummyuser13,dummyuser14,dummyuser15,dummyuser16,dummyuser17,dummyuser18,dummyuser19,dummyuser20};
do for articleNo in {1..16}; 
do echo 'Rating('$user'-article'$articleNo'):
 writingStyle: '$((RANDOM % 5 + 1))'
 nonAlignment: '$((RANDOM % 5 + 1))'
 overall: '$((RANDOM % 5 + 1))'
 user: '$user'
 article: article'$articleNo'
 ';
done
done