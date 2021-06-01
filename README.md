# CSV Distance Tally

Yet another school project, sums up the kilometres, giving you the total distance on that date.

## Building and distributing

Not the cleanest process, but it does get the job done.

```sh
$ javac -d build src/**/*.java
```

```sh
$ cd build
./build$ jar cmvf ../src/METADATA-INF/MANIFEST.MF ../export.jar **/*.class
```

## data_files folder

The folder to put (probably Google Maps) CSVs with 11 columns of the routes.  
I'd have preferred if it was an argument to specify where to get these files from, but such is the implementation specification.
