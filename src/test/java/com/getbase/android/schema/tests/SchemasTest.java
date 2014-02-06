package com.getbase.android.schema.tests;

import static org.fest.assertions.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;

import com.getbase.android.schema.Schemas;
import com.getbase.android.schema.Schemas.DropTable;
import com.getbase.android.schema.Schemas.TableDowngrade;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(reportSdk = 10, manifest = Config.NONE)
public class SchemasTest {

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
  }

  public void shouldReturnCorrectCurrentSchemaVersion() throws Exception {
    Schemas db = Schemas.Builder
        .currentSchema(2900)
        .build();

    assertThat(db.getCurrentRevisionNumber()).isEqualTo(2900);
  }

  @Test
  public void shouldAllowGettingSchemaForPreviousVersion() throws Exception {
    Schemas db = Schemas.Builder
        .currentSchema(2900)
        .build();

    assertThat(db.getSchema(1500)).isNotNull();
  }

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Test
  public void shouldNotAllowGettingSchemaForVersionHigherThanCurrentVersion() throws Exception {
    expectedException.expectCause(is(CoreMatchers.<IllegalStateException>instanceOf(IllegalStateException.class)));

    Schemas db = Schemas.Builder
        .currentSchema(1500)
        .build();

    db.getSchema(2900);
  }

}
