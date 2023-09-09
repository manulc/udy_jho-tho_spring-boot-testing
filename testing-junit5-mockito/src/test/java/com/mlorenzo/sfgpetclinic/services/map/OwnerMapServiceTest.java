package com.mlorenzo.sfgpetclinic.services.map;

import com.mlorenzo.sfgpetclinic.model.Owner;
import com.mlorenzo.sfgpetclinic.model.PetType;
import com.mlorenzo.sfgpetclinic.services.PetService;
import com.mlorenzo.sfgpetclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

@DisplayName("Owner Map Service Test - ")
class OwnerMapServiceTest {
    OwnerMapService ownerMapService;
    PetTypeService petTypeService;
    PetService petService;

    @BeforeEach
    void setUp() {
        petTypeService = new PetTypeMapService();
        petService = new PetMapService();
        ownerMapService = new OwnerMapService(petTypeService, petService);
        System.out.println("First Before Each");
    }

    // Este mensaje se concatena al mensaje indicado en la anotación @DisplayName que hay a nivel de clase
    @DisplayName("Verify Zero Owners")
    @Test
    void testOwnersAreZero() {
        int ownerCount = ownerMapService.findAll().size();
        // Usando la librería de afirmaciones o "Assertions" "AssertJ"
        assertThat(ownerCount).isEqualTo(0);
    }

    @DisplayName("Pet Type Tests - ")
    @Nested
    class CreatePetTypesTest {

        @BeforeEach
        void setUp() {
            PetType petType1 = new PetType(1L, "Dog");
            PetType petType2 = new PetType(2L, "Cat");
            petTypeService.save(petType1);
            petTypeService.save(petType2);
            System.out.println("Nested Before Each");
        }

        @Test
        void testPetCount() {
            int petTypeCount = petTypeService.findAll().size();
            // Usando la librería de afirmaciones o "Assertions" "AssertJ"
            assertThat(petTypeCount).isNotZero().isEqualTo(2);
        }

        @DisplayName("Save Owners Tests - ")
        @Nested
        class SaveOwnersTest {

            @BeforeEach
            void setUp() {
                ownerMapService.save(new Owner(1L, "Before", "Each"));
                System.out.println("Saved Owners Before Each");
            }

            @Test
            void testSaveOwner() {
                Owner owner = new Owner(2L, "Joe", "Buck");
                Owner savedOwner = ownerMapService.save(owner);
                // Usando la librería de afirmaciones o "Assertions" "AssertJ"
                assertThat(savedOwner).isNotNull();
            }

            @DisplayName("Find Owners Tests - ")
            @Nested
            class FindOwnersTest {

                @DisplayName("Find Owner")
                @Test
                void testFindOwner() {
                    Owner foundOwner  = ownerMapService.findById(1L);
                    // Usando la librería de afirmaciones o "Assertions" "AssertJ"
                    assertThat(foundOwner).isNotNull();
                }

                @DisplayName("Find Owner Not Found")
                @Test
                void testFindOwnerNotFound() {
                    Owner foundOwner  = ownerMapService.findById(2L);
                    // Usando la librería de afirmaciones o "Assertions" "AssertJ"
                    assertThat(foundOwner).isNull();
                }
            }
        }
    }

    @DisplayName("Verify Still Zero Owners")
    @Test
    void testOwnersAreStillZero() {
        int ownerCount = ownerMapService.findAll().size();
        // Usando la librería de afirmaciones o "Assertions" "AssertJ"
        assertThat(ownerCount).isEqualTo(0);
    }
}