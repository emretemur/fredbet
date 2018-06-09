package de.fred4jupiter.fredbet.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fred4jupiter.fredbet.domain.Country;
import de.fred4jupiter.fredbet.domain.Match;
import de.fred4jupiter.fredbet.repository.MatchRepository;
import de.fred4jupiter.fredbet.util.MessageSourceUtil;

@Service
public class CountryService {

	@Autowired
	private MessageSourceUtil messageSourceUtil;

	@Autowired
	private MatchRepository matchRepository;

	public List<Country> getAvailableCountriesSortedWithNoneEntryByLocale(Locale locale) {
		LinkedList<Country> result = new LinkedList<>();
		result.addAll(getAvailableCountriesSortedWithoutNoneEntry(locale));
		result.addFirst(Country.NONE);
		return result;
	}

	public List<Country> getAvailableCountriesSortedWithoutNoneEntry(Locale locale) {
		List<Country> countriesWithoutNoneEntry = getAvailableCountriesWithoutNoneEntry();
		return getAvailableCountriesSortedWithoutNoneEntry(locale, countriesWithoutNoneEntry);
	}

	public List<Country> getAvailableCountriesSortedWithoutNoneEntry(Locale locale, List<Country> countriesWithoutNoneEntry) {

		return countriesWithoutNoneEntry.stream()
				.filter(Objects::nonNull)
				.sorted(Comparator.comparing((Country country) -> messageSourceUtil.getCountryName(country, locale)))
				.collect(Collectors.toList());
	}

	public List<Country> getAvailableCountriesWithoutNoneEntry() {
		return Arrays.stream(Country.values()).filter(country -> !country.equals(Country.NONE)).collect(Collectors.toList());
	}

	public List<Country> getAvailableCountriesExtraBetsSortedWithNoneEntryByLocale(Locale locale) {
		final Set<Country> resultset = getAvailableCountriesExtraBetsWithoutNoneEntry();

		LinkedList<Country> result = new LinkedList<>(getAvailableCountriesSortedWithoutNoneEntry(locale, new ArrayList<>(resultset)));
		result.addFirst(Country.NONE);
		return result;
	}

	public Set<Country> getAvailableCountriesExtraBetsWithoutNoneEntry() {
		List<Match> allMatches = matchRepository.findAll();

		final Set<Country> resultset = new HashSet<>();
		allMatches.stream().filter(match -> match != null && (match.getCountryOne() != null || match.getCountryTwo() != null)).forEach(match -> {
			resultset.add(match.getCountryOne());
			resultset.add(match.getCountryTwo());
		});
		return resultset;
	}
}
