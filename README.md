# 📖 Kitap Değerlendirme Web Uygulaması

Java Servlet ve JSP teknolojileri kullanılarak geliştirilmiş bu web uygulaması, kullanıcıların kitapları görüntüleyip puanlama ve yorum yapabildiği, basit ama işlevsel bir kitap değerlendirme sistemidir.

## ✨ Özellikler

- 📚 Kitapları listeleme (başlık, yazar, açıklama)
- 📝 Kullanıcıların kitaplara yorum yapabilmesi
- ⭐ 1 ile 5 arası puanlama sistemi
- 📊 Her kitap için ortalama puan hesaplaması
- 🕓 Kitap ve yorumlar için zaman damgası (timestamp)
- ✅ Form doğrulama ve kullanıcı geri bildirimi
- 🗃️ MySQL veritabanı ile tam entegre yapı

## 🧱 Proje Mimarisi (MVC)

- **Model:** `Book` sınıfı ve `DBUtil` veri erişim katmanı
- **View:** JSP sayfaları (örneğin `addBook.jsp`, `addComment.jsp`,`addRating.jsp`,`bookDetails.jsp`,`home.jsp`,`index.jsp`,`login.jsp`,`register.jsp`,)
- **Controller:** Servlet'ler (örneğin `AddBookServlet`, `AddCommentServlet`,`AddRatingServlet`,`BookDetailServlet`,`HomeServlet`,`LoginServlet`,`LogoutServlet`,`RegisterServlet`,)

## 🧰 Kullanılan Teknolojiler

| Katman        | Teknoloji                      |
|---------------|--------------------------------|
| Backend       | Java (Servlet API), JDBC       |
| Frontend      | JSP, HTML, CSS                 |
| Veritabanı    | MySQL                          |
| IDE           | Eclipse                        |
| Sunucu        | Apache Tomcat                  |
| Yönetim       | MySQL Workbench                |

## 📂 Proje Yapısı

KitapKosem/
|--src/
| |--main/
| | | --java/
| | | |--com/
| | | | |--kitapkosem/
| | | | | |--model/
| | | | | | |--Book.java
| | | | | |--servlet/
| | | | | | |--AddBookServlet.java
| | | | | | |--AddCommentServlet.java
| | | | | | |--AddRatingServlet.java
| | | | | | |--BookDetailServlet.java
| | | | | | |--HomeServlet.java
| | | | | | |--LoginServlet.java
| | | | | | |--LogoutServlet.java
| | | | | | |--RegisterServlet.java
| | | | | |--test/
| | | | | | |--TestConnection.java
| | | | | |--utils/
| | | | | | |--DButil.java
| | | --webapp/
| | | |--META-INF/
| | | | |--MANIFEST.MF
| | | |--WEB-INF/
| | | | |--lib/
| | | | | |--mysql-connector-j-9.3.0.jar
| | | |--addBook.jsp
| | | |--addComment.jsp
| | | |--addRating.jsp
| | | |--bookDetails.jsp
| | | |--home.jsp
| | | |--index.jsp
| | | |--login.jsp
| | | |--register.jsp


## 🚀 Kurulum ve Çalıştırma

1. **Veritabanını içe aktarın:**  
   `kitapkosemyedek.sql` dosyasını MySQL Workbench ile çalıştırarak `kitap_db` veritabanını oluşturun.

2. **Bağlantı bilgilerini güncelleyin:**  
   `DBUtil.java` dosyasında veritabanı bağlantı bilgilerini (host, kullanıcı adı, şifre) kendi sisteminize göre uyarlayın.

3. **Projeyi Eclipse’e aktarın:**  
   - Eclipse > `File > Import > Existing Projects into Workspace`
   - Tomcat sunucusunu tanımlayıp projeyi `Run on Server` ile başlatın.

4. **Tarayıcıda açın:**  
   `http://localhost:8080/KitapKosem/`

## 🧪 Test Senaryoları

- Bir kitap listesine gidin ve detay sayfasını görüntüleyin.
- Yorum ve puan ekleyin; ortalama puanın güncellendiğini doğrulayın.
- Yorum yapmak veya puan vermek için kayıt olunması gerektiğini görün
- Kayıt olurken mevcut hesap varsa oraya yönlendirilebileceğini görün


## 🎯 Amaç ve Katkı

Bu uygulama, Java web teknolojileriyle tam işlevsel bir CRUD uygulaması geliştirmenin temel adımlarını içermektedir. Projede; form işlemleri, sunucu-istemci etkileşimi, veritabanı bağlantısı, MVC mimarisi ve kullanıcı deneyimi gibi önemli konular başarıyla entegre edilmiştir.

---


