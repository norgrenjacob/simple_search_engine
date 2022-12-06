# Simple search engine

## TreeMap

The solution is implemented using TreeMaps in an attempt to make
the solution more scalable. Altough java.util claims a HashMap lookup takes O(1),
problems may arise as the volyme of data grows.

## DocOccPair & DocWeightPair

Storing data pairs that are related.
Objects take up space and these may not be necessary.

### DocOccPair

Stores (Document ID, # occurrences in document) and is associated
with a key that represents a term.

### DocWeightPair

Stores (Document ID, tf_idf score) and is associated with a key as well.
