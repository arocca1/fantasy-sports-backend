# fantasy-sports-backend
The backend of a fantasy sports application. The goal is to support any sport, and support any database/file system backing. This is just a set of APIs. A front-facing app is required separately

Note: Development currently in progress

To set up your persistence settings, you will want to edit src/META-INF/persistence.xml.
You will need to set hibernate.dialect property, javax.persistence.jdbc.url property,
and the user and password properties. For example, let's say that you are using MySQL.
The properties in your persistence.xml would look something like this:
<br />
\<property name="hibernate.dialect" value="org.hibernate.dialect.MySQLInnoDBDialect"/>
<br />
\<property name="hibernate.hbm2ddl.auto" value="update"/>
<br />
\<property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
<br />
\<property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/fantasy_sports"/>
<br />
\<property name="javax.persistence.jdbc.user" value="web_user"/>
<br />
\<property name="javax.persistence.jdbc.password" value="\<SOME PASSWORD>"/>
