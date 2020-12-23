package mk.finki.ukim.mk.lab;

import mk.finki.ukim.mk.lab.model.Balloon;
import mk.finki.ukim.mk.lab.model.Manufacturer;
import mk.finki.ukim.mk.lab.model.enumerations.BalloonType;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidBalloonDescriptionException;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidBalloonNameException;
import mk.finki.ukim.mk.lab.model.exceptions.InvalidBalloonTypeException;
import mk.finki.ukim.mk.lab.repository.jpa.BalloonRepository;
import mk.finki.ukim.mk.lab.repository.jpa.ManufacturerRepository;
import mk.finki.ukim.mk.lab.service.BalloonService;
import mk.finki.ukim.mk.lab.service.impl.BalloonServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class BalloonAddTest {
    Manufacturer manufacturer1 = new Manufacturer("manufacturer1", "country1", "address1");
    BalloonType balloonType = BalloonType.HEART;
    Long BALLOON_ID = Integer.toUnsignedLong(1000);
    @Mock
    private BalloonRepository balloonRepository;
    @Mock
    private ManufacturerRepository manufacturerRepository;
    private BalloonService service;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);


        Balloon balloon = new Balloon("name", "description", manufacturer1, balloonType);
        balloon.setId(BALLOON_ID);

        Mockito.when(this.balloonRepository.save(Mockito.any(Balloon.class))).thenReturn(balloon);
        Mockito.when(this.manufacturerRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(manufacturer1));

        this.service = Mockito.spy(new BalloonServiceImpl(this.balloonRepository, this.manufacturerRepository));
    }

    @Test
    public void testSuccessBalloonAdd() {
        Balloon balloon = this.service.save("name", "description", manufacturer1.getId(), BalloonType.HEART, null).get();

        Mockito.verify(this.service).save("name", "description", manufacturer1.getId(), BalloonType.HEART, null);

        Assert.assertNotNull("User is null", balloon);
        Assert.assertEquals("Name doesn't match", "name", balloon.getName());
        Assert.assertEquals("Description doesn't match", "description", balloon.getDescription());
        Assert.assertEquals("Manufacturer doesn't match", manufacturer1, balloon.getManufacturer());
        Assert.assertEquals("BalloonType doesn't match", balloonType, balloon.getType());
    }

    @Test
    public void testSuccessBalloonUpdate() {

        Balloon balloon = this.service.save("name", "description", manufacturer1.getId(), BalloonType.HEART, BALLOON_ID).get();

        Mockito.verify(this.service).save("name", "description", manufacturer1.getId(), BalloonType.HEART, BALLOON_ID);
        Mockito.verify(this.balloonRepository).deleteById(BALLOON_ID);
    }

    @Test
    public void testEmptyName() {
        String name = "";

        Assert.assertThrows("InvalidBalloonNameException expected", InvalidBalloonNameException.class,
                () -> this.service.save(name, "description", manufacturer1.getId(), BalloonType.HEART, null));
        Mockito.verify(this.service).save(name, "description", manufacturer1.getId(), BalloonType.HEART, null);
    }

    @Test
    public void testEmptyDescription() {
        String description = "";

        Assert.assertThrows("InvalidBalloonDescriptionException expected", InvalidBalloonDescriptionException.class,
                () -> this.service.save("name", description, manufacturer1.getId(), BalloonType.HEART, null));
        Mockito.verify(this.service).save("name", description, manufacturer1.getId(), BalloonType.HEART, null);
    }

    @Test
    public void testNullType() {

        Assert.assertThrows("InvalidBalloonTypeException expected", InvalidBalloonTypeException.class,
                () -> this.service.save("name", "description", manufacturer1.getId(), null, null));
        Mockito.verify(this.service).save("name", "description", manufacturer1.getId(), null, null);
    }


}
