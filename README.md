This is an adaption of [RoboTest][1] for [specs2][2].

## Use

Import your spec class from `org.specs2.robo` for acceptance-style or
`org.specs2.robo.mutable` for unit-style.

There are two variants in how the Robolectric universe is set up and torn down:

* **ClassSpec** all examples in a class share Robolectric
* **IsoSpec** each example has its isolated Robolectric

## sbt

```sbt
resolvers += Resolver.bintrayRepo("tek", "releases")
libraryDependencies += "tryp" %% "speclectic" % "+"
```

## License

© Torsten Schmits. Distributed under the terms of the [MIT License][3].

[1]: https://github.com/zbsz/robotest 'robotest'
[2]: https://github.com/etorreborre/specs2 'specs2'
[3]: http://opensource.org/licenses/MIT 'mit license'
