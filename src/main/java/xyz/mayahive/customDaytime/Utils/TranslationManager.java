package xyz.mayahive.customDaytime.Utils;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationStore;
import net.kyori.adventure.util.UTF8ResourceBundleControl;

import java.net.URL;
import java.text.MessageFormat;
import java.util.*;

public class TranslationManager {

    private final TranslationStore.StringBased<MessageFormat> store;

    /**
     * Create the translation manager with namespace key.
     *
     * @param namespaceKey a Key ("namespace:value")
     * @param baseName the base name of .properties (e.g. "your.plugin.Bundle")
     */
    public TranslationManager(Key namespaceKey, String baseName) {
        this.store = TranslationStore.messageFormat(namespaceKey);
        registerBundle(baseName);
    }

    /**
     * Loads translations from ResourceBundle files encoded in UTF-8.
     *
     * @param baseName the base name of .properties (e.g. "your.plugin.Bundle")
     */
    public void registerBundle(final String baseName) {
        List<Locale> locales = getLocales(baseName);

        // Go through all locales, then load them
        for (Locale locale : locales) {
            ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, new UTF8ResourceBundleControl());
            store.registerAll(locale, bundle, true);
        }

        // Add this translation store as a source for all translations
        GlobalTranslator.translator().addSource(store);
    }

    private List<Locale> getLocales(String baseName) {

        List<Locale> locales = new ArrayList<>();

        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                ResourceBundle bundle = ResourceBundle.getBundle(baseName, locale, new UTF8ResourceBundleControl());

                if (bundle.getLocale().equals(locale)) {
                    locales.add(locale);
                }
            } catch (MissingResourceException ignored) {}
        }
        return locales;
    }
}
