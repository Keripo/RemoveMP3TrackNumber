Simple command-line tool to strip the prefixing track numbers from music files. Hardcoded to check .mp3, .m4a, or .flac files, too lazy to make this configurable.

Wrote this in Java cause I don't have Linux installed at the moment, nor any other dev environment other than eclipse for that matter and am too lazy to set any up. If you have Linux, just run something like:

```
find ~/Music -type f -name '*.mp3' -exec bash -c 'mv "$0" "${0#[0-9][0-9] }"' {} \;
```

All source code is available under Modified BSD license.

~Keripo