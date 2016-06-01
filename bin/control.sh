
declare -a NAMES=("Edward Libby"
 "P.Leif Bergsagel"
 "Adam.D Cohen"
 "Hermann Einsele"
 "Amrita.Y Krishnan"
 "William Bensinger"
 "Sagar Lonial"
 "Heinz Ludwig"
 "María-Victoria Mateos"
 "P.+ McCarthy"
 "S.Vincent Rajkumar"
 "Paul Richardson"
 "Jatin Shah"
 "Saad Usmani"
 "David Vesole"
 "Ravi Vij")

echo $NAMES

regex="([^ ]+) ([^ ]+)"

for n in "${NAMES[@]}"
do
 if [[ $n =~ $regex ]]
 then
     first="${BASH_REMATCH[1]}"
     last="${BASH_REMATCH[2]}"
     echo "$first $last"
     egrep -i "\"lastName\":\"${last}\",\"firstName\":\"${first}\"" ../data/pubmed_result.json | wc
 fi
done
