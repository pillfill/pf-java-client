/* 
 * The MIT License
 *
 * Copyright 2015 Apothesource, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.apothesource.pillfill.service.pharmacy;

import com.apothesource.pillfill.datamodel.PharmacyType;
import com.apothesource.pillfill.datamodel.PrescriptionType;
import com.apothesource.pillfill.datamodel.android.GooglePlaceSearchResult;
import java.util.List;
import rx.Observable;

/**
 *
 * @author Michael Ramirez (michael@pillfill.com)
 */
public interface PharmacyService {

    /**
     * Get pharmacy entries by their PillFill-generated Pharmacy ID.
     * 
     * @param pharmacyIds The list of pharmacy IDs (separated by commas in the URL)
     * @return The pharmacy entries associated with the provided IDs
     */
    Observable<PharmacyType> getPharmacies(List<String> pharmacyIds);

    /**
     * A convenience method for getPharmacies- Extracts the pharmacy IDs from the pharmacies, then call getPharmacies
     * @param rxList The prescriptions to extract pharmacy IDs
     * @param additionalIds Any additional pharmacy IDs to retrieve
     * @return The associated pharmacy entries
     */
    Observable<PharmacyType> getPharmaciesFromPrescriptions(List<PrescriptionType> rxList, List<String> additionalIds);

    /**
     * A wrapper for google search filtered down to pharmacy-only entries. Returns google pharmacy search results
     * 
     * @param keyword The keyword to search for- e.g. Walgreens, CVS, etc.
     * @param pgtoken The page token (for pagination)
     * @param lat Location (latitude) to search near
     * @param lng Location (longitude) to search near
     * @param radius The radius in Kilometers to search around the provided coordinates
     * @return The list of Google Place Search Results
     */
    Observable<GooglePlaceSearchResult.PlaceSearchResponse> searchForPharmacyPlacesNearLocation(String keyword, String pgtoken, double lat, double lng, int radius);
    /**
     * Used to retrieve specific pharmacy entries from a google search. It converts a Google place search result to a PillFill PharmacyType entity. Used after calling <code>searchForPharmacyPlacesNearLocation</code>
     * @param ref The referral ID from the <code>GooglePlaceSearchResult</code>
     * @return A <code>PharmacyType</code> result from the google result
     * @see com.apothesource.pillfill.datamodel.android.GooglePlaceSearchResult
     */
    Observable<PharmacyType> getPharmacyPlace(String ref);

    
    
}
