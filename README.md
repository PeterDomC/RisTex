<h1>
  RisTex - A citation tool
</h1>

Have you ever had the problem of having just a .ris file but you needed a .bib file instead?
RisTex solves this issue!
Provided a citation entry in RIS format, RisTex transforms it into a bibtex entry and vice versa.

The [RIS format](https://en.wikipedia.org/wiki/RIS_(file_format) "RIS") is a citation format based on simple tags.
It is provided by many websites of publishers, often as the only available citation format.
Below is an example citation entry of one of my works:

```
Provider: Schloss Dagstuhl - Leibniz Center for Informatics
Database: dblp computer science bibliography
Content:text/plain; charset="utf-8"

TY  - CPAPER
ID  - DBLP:conf/fsttcs/ChiniS20
AU  - Chini, Peter
AU  - Saivasan, Prakash
TI  - A Framework for Consistency Algorithms.
BT  - 40th IARCS Annual Conference on Foundations of Software Technology and Theoretical Computer Science, FSTTCS 2020.
SP  - 42:1
EP  - 42:17
PY  - 2020//
ER  -
```

The problem with RIS is that it is not compatible with bibtex - one of the major tools to create bibliographies in LaTex/Tex.
The latter requires citation entries to be in bibtex format (.bib files).
For the entry shown above, this looks as follows:

```
@inproceedings{DBLP:conf/fsttcs/ChiniS20,
  author = {Chini, Peter and Saivasan, Prakash},
  title = {A Framework for Consistency Algorithms},
  booktitle = {40th IARCS Annual Conference on Foundations of Software Technology and Theoretical Computer Science, FSTTCS 2020},
  pages = {42:1--42:17},
  year = {2020}
}
```

The tool RisTex translates one format into another.
Given an entry in RIS format, it generates an entry in bibtex format and vice versa.
Using the tool is simple: just copy the entry that should be translated into the corresponding textbox in the GUI.
