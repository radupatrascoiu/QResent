# QResent

## Informatii
\
&nbsp;

### Componenta echipei



* Vitoga Patrick, 344C5
* Velea Alin, 341C2
* Patrascoiu Radu, 344C5
* Mircia Camelia, 343C5
* Dumitru Andrei, 343C5
* Apostol Alexandru, 341C2


### Organizarea echipei
https://docs.google.com/document/d/1zUTpec2u40c6PFR58w9ZdwB1DsjZbojqtyX4u6uRgX8/edit?usp=sharing

### Scopul proiectului



Se dorește construirea unei aplicații ce vine în sprijinul digitalizării sistemului de prezență în format fizic, dar care să fie pretabil și pentru forma online.

Arhitectura soluției și tehnologiile folosite sunt la latitudinea studenților.



### Descrierea proiectului
Sursă: https://ocw.cs.pub.ro/courses/mps/proiect/proiect-1

Aplicația QResent trebuie să:



Acomodeze utilizatori:
* Administrator: utilizator privilegiat care face managementul utilizatorilor și al aplicației.
* Profesor: utilizator privilegiat care poate gestiona materia, genera coduri pentru prezență
* Student: end-user, vizualizează majoritatea informațiilor

Aceștia pot fi utilizatori autentificați prin alte servicii Google sau Microsoft cu linkare a contului de student. Recomandăm o astfel de variantă.

Permită o gamă de acțiuni întreprinse de profesor pentru a face setup-ul și gestiunea unei materii
* Configurarea materiei: informații despre materie, cerințe minime de intrare în examen, bonusuri, orar etc.
* Setarea intervalelor de activitate
* Generarea de statistici legate de numărul de cursanți activi, cursanți prezenți etc.
* Generarea de coduri QR pentru a verifica prezența:
  * La începutul activității (contribuie la statistica cursanți prezenți)
  * Aleator în cadrul activității(contribuie la statistica cursanți activi)
  * În momente cheie, când dorește acesta să verifice studenții activi.
  * La finalul activității (pentru contorizarea numărul de cursanți activi pe tot parcursul activității, contribui la statistica cursanți activi)
* Export al listei de prezență
        Cu informațiile studenților prezentate într-un mod unitar: de ex, sub șablon: NUME Prenume, grupă, utilizator LDAP (utilizator pentru platforma de curs)
* Istoric al prezenței: pentru fiecare cod generat de profesor, statusul intrării respective.

Permită studentului să:
* vizualizeze informațiile din cadrul materiei
* să vizualizeze statisticile create de profesor legate de numărul de cursanți la curs sub formă de grafice
* să scaneze codurile QR generate de profesor


### Tehnologii folosite

* Spring (Backend) + React (Frontend) + Mongo (Database) + Keycloak (Autentificare/Securitate)
* IDEs: Intellij, Visual Studio
\
&nbsp;
## Implementare
\
&nbsp;


### Diagrama de arhitectura
\
&nbsp;

![Diagrama de arhitectura](./Diagrama%20de%20arhitectura.png)
\
&nbsp;

### Schema bazei de date
\
&nbsp;

![Schema bazei de date](./Database%20schema.png)
\
&nbsp;
\
&nbsp;
\
&nbsp;
### Flow pentru logica din spatele codurilor QR
\
&nbsp;

![Flow QR](./QRflow.png)
