package iuh.fit.dhktpm117ctt.group06.entities;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @NotNull(message = "HOME_NUMBER_INVALID")
    @NotBlank(message = "HOME_NUMBER_INVALID")
    private String homeNumber;
    @NotNull(message = "STREET_INVALID")
    @NotBlank(message = "STREET_INVALID")
    private String street;
    @NotNull(message = "WARD_INVALID")
    @NotBlank(message = "WARD_INVALID")
    private String ward;
    @NotNull(message = "DISTRICT_INVALID")
    @NotBlank(message = "DISTRICT_INVALID")
    private String district;
    @NotNull(message = "CITY_INVALID")
    @NotBlank(message = "CITY_INVALID")
    private String city;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
