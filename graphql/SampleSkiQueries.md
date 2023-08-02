# http://snowtooth.moonhighway.com/
```
query lifts {
  allLifts {
    name
    status
  }
}# Write your query or mutation here


query trails {
  allTrails {
    name
    difficulty
  }
}

query closedLifts {
  allLifts(status: CLOSED){
    name
    status
  }
}

#Two datasets in single query
query liftsAndTrails {
  liftCount(status: OPEN)
  chairLifts:allLifts {
    name
    status
  }
  skiSlopes: allTrails {
    name
    difficulty
  }
}

query trailsAccessedByJazzCat {
  Lift(id:"jazz-cat") {
    capacity
    trailAccess {
      name
      difficulty
    }
  }
}

#Reusable piece of select
fragment liftInfo on Lift {
  name
  status
  capacity
  night
  elevationGain
}

#Using Fragments
query withFragments {
  Lift(id:"jazz-cat") {
    ...liftInfo
    trailAccess {
      name
      difficulty
    }
  }
  Trail(id:"river-run") {
    name
    difficulty
    accessedByLifts {
      ...liftInfo
    }
  }
}

#Using subscriptions
subscription subscription {
  liftStatusChange {
    name
    capacity
    status
	} 
}

#Mutation
mutation closeLift {
        setLiftStatus(id: "astra-express" status: CLOSED) {
name
status }
}
```