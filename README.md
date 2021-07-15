# CSV Distance Tally

Yet another school project, calculates the change in degrees to get distance travelled

## Building and distributing

Not the cleanest process, but it does get the job done.

```sh
$ javac -d build src/**/*.java
```

```sh
$ cd build
$ jar cmvf ../src/METADATA-INF/MANIFEST.MF ../export.jar **/*.class
```

## data_files folder

The folder to put CSVs from CanWay's exports to (should have 11 columns).  
I'd have preferred if it was an argument to specify where to get these files from, but such is the implementation specification.
