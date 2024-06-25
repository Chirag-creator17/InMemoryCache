package chirg.dev.cache;

import chirg.dev.cache.eviction_policies.LIFOPolicy;

public class Runner {

    public static void main(String[] args) {

        Cache<String, String> phoneBook = new Cache<>(
                3,
                new LIFOPolicy<>()
        );

        try {
            phoneBook.put("Chiru", "Goel");
            phoneBook.put("Rishu", "Gupta");
            phoneBook.put("Golu", "Gupta");
            phoneBook.put("Atishay", "Jain");

            String val = phoneBook.get("Atishay");
            System.out.println(val);

            phoneBook.put("Chiru", "Goel");
            System.out.println(phoneBook.get("Chiru"));

            phoneBook.put("Chiru", "goel");
            System.out.println(phoneBook.get("Chiru"));

            System.out.println(phoneBook.get("Rishu"));
            phoneBook.put("Rishu", "Gupta1");
            System.out.println(phoneBook.get("Rishu"));

            phoneBook.clearCache();

            val = phoneBook.get("Atishay");
            System.out.println(val);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
